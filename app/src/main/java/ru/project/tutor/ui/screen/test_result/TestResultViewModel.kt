package ru.project.tutor.ui.screen.test_result

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import ru.project.tutor.ads.AppTestResultBannerAdLoader
import ru.project.tutor.ads.InterstitialResultListener
import ru.project.tutor.analytics.AppAnalytics
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.domain.repository.AttemptRepository
import ru.project.tutor.domain.repository.ErrorLogger
import ru.project.tutor.domain.usecases.GetDontShowTooErrorsDialogAgainUseCase
import ru.project.tutor.domain.usecases.SetDontShowTooErrorsDialogAgainUseCase
import ru.project.tutor.ui.models.AnswerChoiceDataUi
import ru.project.tutor.ui.models.QuestionForTestProcessUi
import ru.project.tutor.ui.models.QuestionWorkoutUi
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.ui.screen.test_process.composable.QuestionStatus
import ru.project.tutor.ui.screen.test_result.compose.QuestionStatusTestResult
import ru.project.tutor.utils.AdStarterSharedFlow
import ru.project.tutor.utils.withIO

class TestResultViewModel constructor(
    reducer: TestResultReducer,
    private val attemptRepository: AttemptRepository,
    private val adStarterSharedFlow: AdStarterSharedFlow,
    private val resultListener: InterstitialResultListener,
    private val setDontShowTooErrorsDialogAgainUseCase: SetDontShowTooErrorsDialogAgainUseCase,
    private val getDontShowTooErrorsDialogAgainUseCase: GetDontShowTooErrorsDialogAgainUseCase,
    private val errorLogger: ErrorLogger,
    val bannerAdLoader: AppTestResultBannerAdLoader,
    private val testId: Int,
    private val attemptId: Int,
) : ScreenViewModel<TestResultStore.State, TestResultStore.Event, TestResultStore.SideEffect, TestResultStore.Effect, TestResultStore.UiState>(
    reducer
) {

    init {
        viewModelScope.launch {
            forceEffect(
                TestResultStore.Effect.UpdateIsDontShowTooErrorsDialogAgain(
                    isDontShowTooErrorsDialogAgain = getDontShowTooErrorsDialogAgainUseCase()
                )
            )
            resultListener.onAdDismissedFlow.collect {
                sendSideEffect(TestResultStore.SideEffect.Close)
            }
        }
    }

    override fun createState(): TestResultStore.State {
        return TestResultStore.State()
    }

    override
    fun handleEvent(
        currentState: TestResultStore.State,
        intent: TestResultStore.Event,
    ): Flow<TestResultStore.Effect> {
        return when (intent) {
            is TestResultStore.Event.Close -> flow {
                val countErrors = currentState.questions.count { question ->
                    question.questionStatus == QuestionStatusTestResult.ERROR
                }
                if (!currentState.isDontShowAgainCheckbox && countErrors > currentState.questions.size / 2) {
                    emit(TestResultStore.Effect.ShowTooManyErrorsDialog)
                } else {
                    adStarterSharedFlow.start()
                }
            }

            is TestResultStore.Event.CloseTooManyErrorsDialog -> flow {
                AppAnalytics.closeManyError()
                emit(TestResultStore.Effect.CloseTooManyErrorsDialog)
                sendSideEffect(TestResultStore.SideEffect.Close)
            }

            is TestResultStore.Event.ToggleDontShowAgainCheckbox -> flow {
                setDontShowTooErrorsDialogAgainUseCase(
                    isDontShowAgainCheckbox = !currentState.isDontShowAgainCheckbox
                )
                emit(TestResultStore.Effect.ToggleDontShowAgainCheckbox)
            }

            is TestResultStore.Event.ShowErrorsList -> flow {
                AppAnalytics.manyErrorDialogClickErrorList()
                emit(TestResultStore.Effect.CloseTooManyErrorsDialog)
                sendSideEffect(TestResultStore.SideEffect.Close)
                sendSideEffect(
                    TestResultStore.SideEffect.OpenErrorsList(
                        testId,
                        TestsListType.ERRORS
                    )
                )
            }

            is TestResultStore.Event.StartTesting -> flow {
                AppAnalytics.manyErrorDialogClickStartTest()
                emit(TestResultStore.Effect.CloseTooManyErrorsDialog)
                sendSideEffect(TestResultStore.SideEffect.Close)
                sendSideEffect(
                    TestResultStore.SideEffect.OpenStartTesting(
                        testId,
                        TestsListType.ERRORS
                    )
                )
            }

            is TestResultStore.Event.Init -> flow {
                bannerAdLoader.reloadBanner()
                val questionJson = withIO { attemptRepository.getQuestions(attemptId) }

                if (questionJson.isBlank()) {
                    sendSideEffect(TestResultStore.SideEffect.Close)
                    return@flow
                }

                val questionUis: List<QuestionForTestProcessUi> = try {
                    Json.decodeFromString(questionJson)
                } catch (e: SerializationException) {
                    errorLogger.logException(e)
                    sendSideEffect(TestResultStore.SideEffect.Close)
                    return@flow
                }

                val selectedQuestions = questionUis.map { questionWorkout ->
                    QuestionWorkoutUi(
                        question = questionWorkout.question,
                        answers = questionWorkout.answers.map { answer ->
                            AnswerChoiceDataUi(
                                answerChoiceData = answer.answerChoiceData,
                                isSelectedAnswer = answer.isSelectedAnswer
                            )
                        },
                        questionStatus = when (questionWorkout.questionStatus) {
                            QuestionStatus.CORRECT -> QuestionStatusTestResult.CORRECT
                            else -> QuestionStatusTestResult.ERROR
                        }
                    )
                }

                emit(
                    TestResultStore.Effect.LoadScreen(
                        questions = selectedQuestions,
                    )
                )
            }

            is TestResultStore.Event.SelectAnswerFilter -> flow {
                when (intent.filter) {
                    TestResultStore.AnswerFilter.ALL -> AppAnalytics.testResultFilterAll()
                    TestResultStore.AnswerFilter.CORRECT -> AppAnalytics.testResultFilterCorrect()
                    TestResultStore.AnswerFilter.INCORRECT -> AppAnalytics.testResultFilterIncorrect()
                }
                emit(TestResultStore.Effect.UpdateAnswerFilter(intent.filter))
            }
        }
    }

    override fun handleEffect(
        currentState: TestResultStore.State,
        effect: TestResultStore.Effect,
    ): TestResultStore.State {
        return when (effect) {
            is TestResultStore.Effect.LoadScreen -> {
                val newState = currentState.copy(
                    questions = effect.questions,
                    allQuestions = effect.questions,
                )
                newState
            }

            is TestResultStore.Effect.ToggleDontShowAgainCheckbox -> currentState.copy(
                isDontShowAgainCheckbox = !currentState.isDontShowAgainCheckbox
            )

            is TestResultStore.Effect.ShowTooManyErrorsDialog -> currentState.copy(
                isErrorDialogVisible = true
            )

            is TestResultStore.Effect.CloseTooManyErrorsDialog -> currentState.copy(
                isErrorDialogVisible = false
            )

            is TestResultStore.Effect.UpdateIsDontShowTooErrorsDialogAgain -> currentState.copy(
                isDontShowAgainCheckbox = effect.isDontShowTooErrorsDialogAgain
            )

            is TestResultStore.Effect.UpdateAnswerFilter -> {
                val filteredQuestions = when (effect.filter) {
                    TestResultStore.AnswerFilter.ALL -> currentState.allQuestions
                    TestResultStore.AnswerFilter.CORRECT -> currentState.allQuestions.filter {
                        it.questionStatus == QuestionStatusTestResult.CORRECT
                    }

                    TestResultStore.AnswerFilter.INCORRECT -> currentState.allQuestions.filter {
                        it.questionStatus == QuestionStatusTestResult.ERROR
                    }
                }
                currentState.copy(
                    answerFilter = effect.filter,
                    questions = filteredQuestions,
                )
            }
        }
    }
}
