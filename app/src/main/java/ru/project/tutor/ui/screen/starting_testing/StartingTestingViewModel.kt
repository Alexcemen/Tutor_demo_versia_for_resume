package ru.project.tutor.ui.screen.starting_testing

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import ru.project.tutor.R
import ru.project.tutor.analytics.AppAnalytics
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.domain.models.test.TestWithQuestionsData
import ru.project.tutor.domain.repository.ErrorRepository
import ru.project.tutor.domain.repository.FavoriteRepository
import ru.project.tutor.domain.repository.QuestionRepository
import ru.project.tutor.domain.repository.TestRepository
import ru.project.tutor.ui.models.options_for_testing.OptionsStartTestingUi
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.ui.screen.test_process.TestMode
import ru.project.tutor.utils.AppResource
import ru.project.tutor.utils.withIO

class StartingTestingViewModel constructor(
    reducer: StartingTestingReducer,
    private val testRepository: TestRepository,
    private val questionRepository: QuestionRepository,
    private val favoriteRepository: FavoriteRepository,
    private val errorRepository: ErrorRepository,
    private val appResource: AppResource,
    private val testId: Int,
    private val typeTest: TestsListType,
) : ScreenViewModel<StartingTestingStore.State, StartingTestingStore.Event, StartingTestingStore.SideEffect, StartingTestingStore.Effect, StartingTestingStore.UiState>(
    reducer
) {

    override fun createState(): StartingTestingStore.State =
        StartingTestingStore.State(
            isExam = false,
            optionsStartTestingUi = OptionsStartTestingUi(
                isTimerEnabled = false,
                durationTesting = -1,
                shuffleQuestions = false,
                showRightAnswer = false,
                shuffleAnswers = false,
                doNotMarkMultipleAnswerChoice = false,
                isRandomQuestionsEnable = false,
                countRandomQuestions = -1
            )
        )

    override
    fun handleEvent(
        currentState: StartingTestingStore.State,
        intent: StartingTestingStore.Event,
    ): Flow<StartingTestingStore.Effect> {
        return when (intent) {
            is StartingTestingStore.Event.Close -> flow {
                sendSideEffect(StartingTestingStore.SideEffect.Close)
            }

            is StartingTestingStore.Event.Reload -> flow {
                val testWithQuestionsData = withIO {
                    when (typeTest) {
                        TestsListType.NORMAL -> testRepository.getTestWithQuestions(testId)
                        TestsListType.FAVORITE -> {
                            val test = testRepository.getTest(testId)
                            val favorites = favoriteRepository.getFavoritesByTestId(testId).first()
                            val questions = favorites.mapNotNull { favorite ->
                                questionRepository.getQuestionWithAnswersByQuestionId(favorite.questionId)
                            }
                            TestWithQuestionsData(
                                testData = test,
                                questions = questions
                            )
                        }

                        TestsListType.ERRORS -> {
                            val test = testRepository.getTest(testId)
                            val errors = errorRepository.getErrorsByTestId(testId).first()
                            val questions = errors.mapNotNull { error ->
                                questionRepository.getQuestionWithAnswersByQuestionId(error.questionId)
                            }
                            TestWithQuestionsData(
                                testData = test,
                                questions = questions
                            )
                        }
                    }
                }
                emit(StartingTestingStore.Effect.LoadScreen(testWithQuestionsData))
            }

            is StartingTestingStore.Event.ToggleExamMode -> flow {
                AppAnalytics.switchModeTesting(intent.isExam)
                when (intent.isExam) {
                    true -> emit(StartingTestingStore.Effect.UpdateOptionsForExamMode)
                    false -> emit(StartingTestingStore.Effect.UpdateOptionsForWorkoutMode)
                }
                emit(StartingTestingStore.Effect.ToggleExamMode(intent.isExam))
            }

            is StartingTestingStore.Event.ToggleDurationOption -> flow {
                AppAnalytics.setToggleDuration(currentState.optionsStartTestingUi.isTimerEnabled)
                emit(StartingTestingStore.Effect.UpdateDuration(duration = -1))
                emit(StartingTestingStore.Effect.ToggleDurationOption)
            }

            is StartingTestingStore.Event.ToggleShuffleQuestions -> flow {
                AppAnalytics.setToggleShuffleQuestion(currentState.optionsStartTestingUi.shuffleQuestions)
                emit(StartingTestingStore.Effect.ToggleShuffleQuestions)
            }

            is StartingTestingStore.Event.ToggleShuffleAnswers -> flow {
                AppAnalytics.setToggleShuffleAnswer(currentState.optionsStartTestingUi.shuffleAnswers)
                emit(StartingTestingStore.Effect.ToggleShuffleAnswers)
            }

            is StartingTestingStore.Event.ToggleShowRightAnswer -> flow {
                AppAnalytics.setToggleShowAnswer(currentState.optionsStartTestingUi.showRightAnswer)
                emit(StartingTestingStore.Effect.ToggleShowRightAnswer)
            }

            is StartingTestingStore.Event.ToggleTagMultipleAnswerChoice -> flow {
                AppAnalytics.setMultipleAnswerChoice(currentState.optionsStartTestingUi.doNotMarkMultipleAnswerChoice)
                emit(StartingTestingStore.Effect.ToggleTagMultipleAnswerChoice)
            }

            is StartingTestingStore.Event.ToggleRandomQuestionsOption -> flow {
                emit(StartingTestingStore.Effect.UpdateCountRandomQuestions(countRandomQuestions = -1))
                emit(StartingTestingStore.Effect.ToggleRandomQuestionsOption)
            }

            is StartingTestingStore.Event.UpdateDuration -> flow {
                emit(
                    StartingTestingStore.Effect.UpdateDuration(
                        duration = intent.duration
                    )
                )
            }

            is StartingTestingStore.Event.UpdateCountRandomQuestions -> flow {
                emit(
                    StartingTestingStore.Effect.UpdateCountRandomQuestions(
                        countRandomQuestions = intent.countRandomQuestions
                    )
                )
            }

            is StartingTestingStore.Event.ActionBottom -> flow {
                val mode = if (intent.isExam) TestMode.EXAM else TestMode.WORKOUT
                if (mode == TestMode.EXAM) {
                    emit(StartingTestingStore.Effect.UpdateOptionsForExamMode)
                }
                val options = currentState.optionsStartTestingUi
                val duration = currentState.optionsStartTestingUi.durationTesting
                val needTimer = currentState.optionsStartTestingUi.isTimerEnabled
                val randomQuestionsEnable =
                    currentState.optionsStartTestingUi.isRandomQuestionsEnable
                val countRandomQuestions = currentState.optionsStartTestingUi.countRandomQuestions
                val countQuestions = currentState.testWithQuestionsData?.questions?.size ?: -1

                if (needTimer && duration < 1) {
                    emit(
                        StartingTestingStore.Effect.VisibleBottomSheet(
                            testId = currentState.testId,
                            message = appResource.getString(R.string.toast_min_timer)
                        )
                    )
                    return@flow
                }

                if (randomQuestionsEnable && countRandomQuestions !in 1..countQuestions) {
                    emit(
                        StartingTestingStore.Effect.VisibleBottomSheet(
                            testId = currentState.testId,
                            message = appResource.getString(
                                R.string.toast_min_count_random_questions,
                                countQuestions
                            )
                        )
                    )
                    return@flow
                }

                AppAnalytics.startTest(mode.name.lowercase())
                sendSideEffect(
                    StartingTestingStore.SideEffect.OpenTestProcess(
                        mode,
                        typeTest,
                        testId,
                        options
                    )
                )
            }

            is StartingTestingStore.Event.ShowMessageBottomSheet -> flow {
                emit(StartingTestingStore.Effect.VisibleBottomSheet(testId = -1))
                emit(
                    StartingTestingStore.Effect.VisibleBottomSheet(
                        testId = currentState.testId,
                        message = intent.message
                    )
                )
            }

            is StartingTestingStore.Event.CloseBottomSheet -> flow {
                emit(
                    StartingTestingStore.Effect.VisibleBottomSheet(
                        testId = -1
                    )
                )
            }
        }
    }

    override fun handleEffect(
        currentState: StartingTestingStore.State,
        effect: StartingTestingStore.Effect,
    ): StartingTestingStore.State {
        return when (effect) {
            is StartingTestingStore.Effect.LoadScreen -> currentState.copy(
                testWithQuestionsData = effect.test
            )

            is StartingTestingStore.Effect.ToggleExamMode -> currentState.copy(
                isExam = effect.isExam,
            )

            is StartingTestingStore.Effect.ToggleDurationOption -> currentState.copy(
                optionsStartTestingUi = currentState.optionsStartTestingUi.copy(
                    isTimerEnabled = !currentState.optionsStartTestingUi.isTimerEnabled
                )
            )

            is StartingTestingStore.Effect.ToggleShuffleQuestions -> currentState.copy(
                optionsStartTestingUi = currentState.optionsStartTestingUi.copy(
                    shuffleQuestions = !currentState.optionsStartTestingUi.shuffleQuestions
                )
            )

            is StartingTestingStore.Effect.ToggleShuffleAnswers -> currentState.copy(
                optionsStartTestingUi = currentState.optionsStartTestingUi.copy(
                    shuffleAnswers = !currentState.optionsStartTestingUi.shuffleAnswers
                )
            )

            is StartingTestingStore.Effect.ToggleShowRightAnswer -> currentState.copy(
                optionsStartTestingUi = currentState.optionsStartTestingUi.copy(
                    showRightAnswer = !currentState.optionsStartTestingUi.showRightAnswer
                )
            )

            is StartingTestingStore.Effect.ToggleTagMultipleAnswerChoice -> currentState.copy(
                optionsStartTestingUi = currentState.optionsStartTestingUi.copy(
                    doNotMarkMultipleAnswerChoice = !currentState.optionsStartTestingUi.doNotMarkMultipleAnswerChoice
                )
            )

            is StartingTestingStore.Effect.ToggleRandomQuestionsOption -> currentState.copy(
                optionsStartTestingUi = currentState.optionsStartTestingUi.copy(
                    isRandomQuestionsEnable = !currentState.optionsStartTestingUi.isRandomQuestionsEnable
                )
            )

            is StartingTestingStore.Effect.UpdateDuration -> currentState.copy(
                optionsStartTestingUi = currentState.optionsStartTestingUi.copy(
                    durationTesting = effect.duration
                )
            )

            is StartingTestingStore.Effect.UpdateCountRandomQuestions -> currentState.copy(
                optionsStartTestingUi = currentState.optionsStartTestingUi.copy(
                    countRandomQuestions = effect.countRandomQuestions
                )
            )

            is StartingTestingStore.Effect.VisibleBottomSheet ->
                currentState.copy(
                    isVisibleBottomSheet = effect.testId != -1,
                    bottomSheetMessage = effect.message.ifEmpty { currentState.bottomSheetMessage }
                )

            is StartingTestingStore.Effect.UpdateOptionsForExamMode -> currentState.copy(
                optionsStartTestingUi = currentState.optionsStartTestingUi.copy(
                    isTimerEnabled = true,
                    shuffleQuestions = true,
                    shuffleAnswers = true,
                    showRightAnswer = false,
                    doNotMarkMultipleAnswerChoice = true
                )
            )

            is StartingTestingStore.Effect.UpdateOptionsForWorkoutMode -> currentState.copy(
                optionsStartTestingUi = currentState.optionsStartTestingUi.copy(
                    isTimerEnabled = false,
                    shuffleQuestions = false,
                    showRightAnswer = false,
                    shuffleAnswers = false,
                    doNotMarkMultipleAnswerChoice = false
                )
            )
        }
    }
}