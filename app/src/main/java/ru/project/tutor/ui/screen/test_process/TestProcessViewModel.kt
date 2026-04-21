package ru.project.tutor.ui.screen.test_process

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.project.tutor.ads.AppBannerAdLoader
import ru.project.tutor.ads.InterstitialResultListener
import ru.project.tutor.analytics.AppAnalytics
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.domain.repository.ErrorLogger
import ru.project.tutor.domain.usecases.CheckAnswerQuestionUseCase
import ru.project.tutor.domain.usecases.CompletedTestUseCase
import ru.project.tutor.domain.usecases.CreateAttemptUseCase
import ru.project.tutor.domain.usecases.CreateFavoriteUseCase
import ru.project.tutor.domain.usecases.DeleteFavoriteByQuestionIdUseCase
import ru.project.tutor.domain.usecases.FindNextPendingQuestionIndexUseCase
import ru.project.tutor.domain.usecases.GetErrorsByTestIdUseCase
import ru.project.tutor.domain.usecases.GetFavoritesByTestIdUseCase
import ru.project.tutor.domain.usecases.GetQuestionWithAnswersByQuestionIdUseCase
import ru.project.tutor.domain.usecases.GetQuestionsWithAnswersByTestIdUseCase
import ru.project.tutor.domain.usecases.ShuffleAnswersUseCase
import ru.project.tutor.domain.usecases.ShuffleQuestionsUseCase
import ru.project.tutor.ui.models.options_for_testing.OptionsStartTestingUi
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.ui.screen.test_process.TestProcessViewModelUtil.copyCommon
import ru.project.tutor.ui.screen.test_process.TestProcessViewModelUtil.replaceById
import ru.project.tutor.ui.screen.test_process.composable.QuestionStatus
import ru.project.tutor.utils.AdStarterSharedFlow

class TestProcessViewModel constructor(
    reducer: TestProcessReducer,
    private val checkAnswerQuestionUseCase: CheckAnswerQuestionUseCase,
    private val adStarterSharedFlow: AdStarterSharedFlow,
    private val interstitialResultListener: InterstitialResultListener,
    val bannerAdLoader: AppBannerAdLoader,
    private val createAttemptUseCase: CreateAttemptUseCase,
    private val getQuestionsWithAnswersByTestIdUseCase: GetQuestionsWithAnswersByTestIdUseCase,
    private val getQuestionWithAnswersByQuestionIdUseCase: GetQuestionWithAnswersByQuestionIdUseCase,
    private val getFavoritesByTestIdUseCase: GetFavoritesByTestIdUseCase,
    private val deleteFavoriteByQuestionIdUseCase: DeleteFavoriteByQuestionIdUseCase,
    private val createFavoriteUseCase: CreateFavoriteUseCase,
    private val getErrorsByTestIdUseCase: GetErrorsByTestIdUseCase,
    private val shuffleQuestionsUseCase: ShuffleQuestionsUseCase,
    private val shuffleAnswersUseCase: ShuffleAnswersUseCase,
    private val findNextPendingQuestionIndexUseCase: FindNextPendingQuestionIndexUseCase,
    private val completedTestUseCase: CompletedTestUseCase,
    private val errorLogger: ErrorLogger,
    private val testMode: TestMode,
    private val startingMode: TestsListType,
    private val testId: Int,
    private val options: OptionsStartTestingUi,
) : ScreenViewModel<TestProcessStore.State, TestProcessStore.Event, TestProcessStore.SideEffect, TestProcessStore.Effect, TestProcessStore.UiState>(
    reducer
) {
    init {
        viewModelScope.launch {
            onEvent(
                TestProcessStore.Event.Init
            )
        }
        viewModelScope.launch {
            interstitialResultListener.onAdDismissedFlow.collect {
                onEvent(TestProcessStore.Event.Finish)
            }
        }
    }

    override fun createState(): TestProcessStore.State {
        return TestProcessStore.StateFactory.empty()
    }

    override
    fun handleEvent(
        currentState: TestProcessStore.State,
        intent: TestProcessStore.Event,
    ): Flow<TestProcessStore.Effect> {
        return when (intent) {
            is TestProcessStore.Event.Close -> flow {
                sendSideEffect(TestProcessStore.SideEffect.Close)
            }

            is TestProcessStore.Event.Init -> flow {
                val favoriteDataList = getFavoritesByTestIdUseCase(testId)
                bannerAdLoader.reloadBanner()

                val questionsWithAnswers = when (startingMode) {
                    TestsListType.NORMAL -> getQuestionsWithAnswersByTestIdUseCase(testId = testId)

                    TestsListType.FAVORITE -> favoriteDataList
                        .mapNotNull { getQuestionWithAnswersByQuestionIdUseCase(it.questionId) }

                    TestsListType.ERRORS -> getErrorsByTestIdUseCase(testId = testId)
                        .mapNotNull { getQuestionWithAnswersByQuestionIdUseCase(it.questionId) }
                }

                val countRandomQuestions = options?.countRandomQuestions ?: -1

                val effect = when (testMode) {
                    TestMode.WORKOUT -> {
                        var questionUiList = questionsWithAnswers
                            .let {
                                if (options?.shuffleQuestions == true) shuffleQuestionsUseCase(
                                    questionsWithAnswers = it
                                ) else it
                            }
                            .let {
                                if (options?.shuffleAnswers == true) shuffleAnswersUseCase(
                                    questionsWithAnswers = it
                                ) else it
                            }
                            .let {
                                TestProcessViewModelUtil.mapToQuestionUiList(
                                    questionsWithAnswers = it,
                                    favorites = favoriteDataList
                                )
                            }

                        if (countRandomQuestions != -1) {
                            questionUiList = questionUiList.shuffled().take(countRandomQuestions)
                        }

                        TestProcessStore.Effect.LoadWorkoutScreen(
                            testId = testId,
                            questions = questionUiList,
                            showRightAnswerOption = options.showRightAnswer,
                            durationTesting = options.durationTesting.takeIf { it > 0 } ?: -1,
                            doNotMarkMultipleAnswerChoice = options.doNotMarkMultipleAnswerChoice
                        )
                    }

                    TestMode.EXAM -> {
                        var questionUiList = questionsWithAnswers
                            .let {
                                shuffleQuestionsUseCase(
                                    questionsWithAnswers = it
                                )
                            }
                            .let {
                                shuffleAnswersUseCase(
                                    questionsWithAnswers = it
                                )
                            }
                            .let {
                                TestProcessViewModelUtil.mapToQuestionUiList(
                                    questionsWithAnswers = it,
                                    favorites = favoriteDataList
                                )
                            }

                        if (countRandomQuestions != -1) {
                            questionUiList = questionUiList.shuffled().take(countRandomQuestions)
                        }

                        TestProcessStore.Effect.LoadExamScreen(
                            testId = testId,
                            questions = questionUiList,
                            durationTesting = options.durationTesting.takeIf { it > 0 } ?: -1
                        )
                    }
                }
                emit(effect)
            }

            is TestProcessStore.Event.OpenIconQuestion -> flow {
                emit(TestProcessStore.Effect.OpenIconQuestion(intent.questionNumber))
            }

            is TestProcessStore.Event.ClickOnAnswerChoice -> flow {
                val selectedAnswer = intent.selectedAnswer
                val numberSelectedQuestion = currentState.numberSelectedQuestion

                val currentQuestion = currentState.questions.getOrNull(numberSelectedQuestion - 1)
                if (currentQuestion == null) {
                    errorLogger.logMessage("Question workout is null")
                    return@flow
                }

                if (currentQuestion.isQuestionAnsweredBefore) {
                    return@flow
                }

                val updatedQuestion = TestProcessViewModelUtil.updateAnsweredQuestion(
                    question = currentQuestion,
                    selectedAnswer = selectedAnswer,
                    doNotMarkMultipleAnswerChoice = currentState.doNotMarkMultipleAnswerChoice
                )

                val updatedQuestions = currentState.questions.replaceById(
                    updatedQuestion = updatedQuestion
                )

                emit(
                    TestProcessStore.Effect.UpdateQuestions(
                        updatedQuestions = updatedQuestions
                    )
                )
            }

            is TestProcessStore.Event.CheckAnswerQuestion -> flow {
                val currentQuestion =
                    currentState.questions.getOrNull(currentState.numberSelectedQuestion - 1)
                if (currentQuestion == null) {
                    errorLogger.logMessage("Question workout is null")
                    return@flow
                }

                val checked = checkAnswerQuestionUseCase(
                    testId = currentState.testId,
                    question = currentQuestion,
                )

                val updatedQuestions = currentState.questions.replaceById(checked)
                emit(TestProcessStore.Effect.UpdateQuestions(updatedQuestions = updatedQuestions))
            }

            is TestProcessStore.Event.NextQuestion -> flow {
                val hasUnresolvedAnswer =
                    currentState.questions.any { it.questionStatus == QuestionStatus.UNRESOLVED }

                if (hasUnresolvedAnswer) {
                    emit(TestProcessStore.Effect.NextQuestion)
                } else {
                    completedTestUseCase(
                        testId = currentState.testId,
                        testingStartTime = currentState.testingStartTime
                    )
                    adStarterSharedFlow.start()
                }
            }

            is TestProcessStore.Event.TimerFinished -> flow {
                val updatedQuestions = currentState.questions.map { question ->
                    if (question.questionStatus == QuestionStatus.UNRESOLVED) {
                        question.copy(
                            questionStatus = QuestionStatus.ERROR
                        )
                    } else {
                        question
                    }
                }
                val attemptId = createAttemptUseCase(
                    testId = currentState.testId,
                    questions = updatedQuestions,
                )
                completedTestUseCase(
                    testId = currentState.testId,
                    testingStartTime = currentState.testingStartTime
                )
                sendSideEffect(TestProcessStore.SideEffect.OpenTestResultScreen(testId, attemptId))
            }

            is TestProcessStore.Event.ToggleFavorite -> flow {
                val currentQuestion =
                    currentState.questions.getOrNull(currentState.numberSelectedQuestion - 1)
                if (currentQuestion == null) {
                    errorLogger.logMessage("questions is null")
                    return@flow
                }

                val questionId = currentQuestion.question.id
                val testId = currentState.testId

                if (currentQuestion.isFavorite) {
                    deleteFavoriteByQuestionIdUseCase(questionId)
                } else {
                    createFavoriteUseCase(testId, questionId)
                }

                emit(
                    TestProcessStore.Effect.ToggleFavorite(
                        questionId = questionId,
                        isFavorite = !currentQuestion.isFavorite
                    )
                )
            }

            is TestProcessStore.Event.Finish -> flow {
                sendSideEffect(
                    TestProcessStore.SideEffect.OpenTestResultScreen(
                        testId = testId, attemptId = createAttemptUseCase(
                            testId = currentState.testId,
                            questions = currentState.questions,
                        )
                    )
                )
            }

            is TestProcessStore.Event.ConfirmExitBack -> flow {
                if (currentState.showConfirmExitBottomSheet) return@flow
                emit(TestProcessStore.Effect.ShowConfirmExitBottomSheet(true))
            }

            is TestProcessStore.Event.CloseConfirmExitBottomSheet -> flow {
                if (!currentState.showConfirmExitBottomSheet) return@flow
                emit(TestProcessStore.Effect.ShowConfirmExitBottomSheet(false))
            }

            is TestProcessStore.Event.CloseScreen -> flow {
                if (!currentState.showConfirmExitBottomSheet) return@flow
                AppAnalytics.forceFinishTest()
                emit(TestProcessStore.Effect.ShowConfirmExitBottomSheet(false))
                sendSideEffect(TestProcessStore.SideEffect.CloseScreen)
            }
        }
    }

    override fun handleEffect(
        currentState: TestProcessStore.State,
        effect: TestProcessStore.Effect,
    ): TestProcessStore.State {
        return when (effect) {
            is TestProcessStore.Effect.LoadWorkoutScreen -> {
                TestProcessStore.WorkoutState(
                    testId = effect.testId,
                    questions = effect.questions,
                    showRightAnswerOption = effect.showRightAnswerOption,
                    durationTesting = effect.durationTesting,
                    numberSelectedQuestion = 1,
                    doNotMarkMultipleAnswerChoice = effect.doNotMarkMultipleAnswerChoice
                )
            }

            is TestProcessStore.Effect.LoadExamScreen -> {
                TestProcessStore.ExamState(
                    testId = effect.testId,
                    questions = effect.questions,
                    durationTesting = effect.durationTesting,
                    numberSelectedQuestion = 1
                )
            }

            is TestProcessStore.Effect.OpenIconQuestion -> {
                currentState.copyCommon(
                    numberSelectedQuestion = effect.questionNumber,
                )
            }

            is TestProcessStore.Effect.UpdateQuestions -> {
                currentState.copyCommon(questions = effect.updatedQuestions)
            }

            is TestProcessStore.Effect.NextQuestion -> {
                val nextIndex = findNextPendingQuestionIndexUseCase(currentState)
                return if (nextIndex == -1) {
                    currentState
                } else {
                    currentState.copyCommon(
                        numberSelectedQuestion = nextIndex + 1
                    )
                }
            }

            is TestProcessStore.Effect.ToggleFavorite -> {
                val updateQuestions = currentState.questions.map { ui ->
                    if (ui.question.id == effect.questionId) {
                        ui.copy(
                            isFavorite = effect.isFavorite
                        )
                    } else ui
                }
                currentState.copyCommon(questions = updateQuestions)
            }

            is TestProcessStore.Effect.ShowConfirmExitBottomSheet -> {
                currentState.copyCommon(showConfirmExitBottomSheet = effect.visible)
            }
        }
    }
}

