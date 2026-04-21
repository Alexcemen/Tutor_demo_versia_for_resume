package ru.project.tutor.ui.screen.test_manager

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import ru.project.tutor.analytics.AppAnalytics
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.common_ui.composable.utils.KeeperKeys
import ru.project.tutor.domain.models.AnswerShareData
import ru.project.tutor.domain.models.QuestionShareData
import ru.project.tutor.domain.models.TestShareData
import ru.project.tutor.domain.models.question_with_answers.QuestionWithAnswersData
import ru.project.tutor.domain.repository.QuestionRepository
import ru.project.tutor.domain.repository.TestRepository
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.utils.withIO
import timber.log.Timber

class TestManagerViewModel constructor(
    reducer: TestManagerReducer,
    private val testRepository: TestRepository,
    private val questionRepository: QuestionRepository,
    private val context: Context,
    private val testId: Int,
) : ScreenViewModel<TestManagerStore.State, TestManagerStore.Event, TestManagerStore.SideEffect, TestManagerStore.Effect, TestManagerStore.UiState>(
    reducer
) {

    private val log = Timber.tag(TestManagerViewModel::class.java.name)
    private val jsonBuilder = Json { prettyPrint = true }

    init {
        viewModelScope.launch {
            val testData = withIO { testRepository.getTest(testId) }
            val questions = withIO { questionRepository.getQuestionsWithAnswersByTestId(testId) }
            forceEffect(
                TestManagerStore.Effect.LoadScreen(
                    testData = testData,
                    questions = questions
                )
            )
        }
    }

    override fun createState(): TestManagerStore.State {
        return TestManagerStore.State()
    }

    override fun handleEffect(
        currentState: TestManagerStore.State,
        effect: TestManagerStore.Effect,
    ): TestManagerStore.State {
        return when (effect) {
            is TestManagerStore.Effect.LoadScreen -> currentState.copy(
                testData = effect.testData,
                allQuestions = effect.questions,
                testId = effect.testData.id,
                visibleQuestions = effect.questions
            )

            is TestManagerStore.Effect.VisibleQuestionEditBottomSheet -> {
                currentState.copy(
                    isVisibleBottomSheet = effect.questionId != -1,
                    questionEditBottomSheetId = effect.questionId
                )
            }

            is TestManagerStore.Effect.VisibleNotificationAboutDeleteBottomSheet -> {
                currentState.copy(
                    notificationAboutDeleteBottomSheetId = effect.testId
                )
            }

            is TestManagerStore.Effect.VisibleNoQuestionsNotificationBottomSheet ->
                currentState.copy(
                    noQuestionsNotificationBottomSheetId = effect.testId
                )

            is TestManagerStore.Effect.VisibleSettingBottomSheet ->
                currentState.copy(
                    settingsBottomSheetId = effect.testId
                )

            is TestManagerStore.Effect.DeleteQuestion -> currentState.copy(
                allQuestions = currentState.allQuestions.filterNot {
                    it.question.id == effect.questionId
                },
                isVisibleBottomSheet = false,
                questionEditBottomSheetId = -1
            )

            is TestManagerStore.Effect.DeleteTest -> currentState

            is TestManagerStore.Effect.ToggleShowSearchField -> currentState.copy(
                showSearchField = effect.showSearchField
            )

            is TestManagerStore.Effect.ClearSearch -> currentState.copy(
                searchQuery = "",
                visibleQuestions = currentState.allQuestions
            )

            is TestManagerStore.Effect.UpdateSearchResult -> currentState.copy(
                searchQuery = effect.query,
                visibleQuestions = effect.filteredQuestions
            )
        }
    }

    override fun handleEvent(
        currentState: TestManagerStore.State,
        intent: TestManagerStore.Event,
    ): Flow<TestManagerStore.Effect> {
        return when (intent) {
            is TestManagerStore.Event.Close -> flow {
                sendSideEffect(TestManagerStore.SideEffect.Close)
            }

            is TestManagerStore.Event.CreateQuestion -> flow {
                sendSideEffect(
                    TestManagerStore.SideEffect.OpenQuestionFactory(
                        currentState.testId,
                        -1
                    )
                )
            }

            is TestManagerStore.Event.OpenQuestionEdit -> flow {
                sendSideEffect(
                    TestManagerStore.SideEffect.OpenQuestionFactory(
                        currentState.testId,
                        intent.questionId
                    )
                )
            }

            is TestManagerStore.Event.CloseQuestionEditBottomSheet -> flow {
                if (currentState.questionEditBottomSheetId == KeeperKeys.CREATE_QUESTION_ID) return@flow
                emit(
                    TestManagerStore.Effect.VisibleQuestionEditBottomSheet(questionId = -1)
                )
            }

            is TestManagerStore.Event.CloseNotificationAboutDeleteBottomSheet -> flow {
                if (currentState.notificationAboutDeleteBottomSheetId == -1) return@flow
                emit(
                    TestManagerStore.Effect.VisibleNotificationAboutDeleteBottomSheet(testId = -1)
                )
            }

            is TestManagerStore.Event.CloseNoQuestionsNotificationBottomSheet -> flow {
                if (currentState.noQuestionsNotificationBottomSheetId == -1) return@flow
                emit(
                    TestManagerStore.Effect.VisibleNoQuestionsNotificationBottomSheet(testId = -1)
                )
            }

            is TestManagerStore.Event.CloseSettingsBottomSheet -> flow {
                if (currentState.settingsBottomSheetId == -1) return@flow
                emit(
                    TestManagerStore.Effect.VisibleSettingBottomSheet(testId = -1)
                )
            }

            is TestManagerStore.Event.OpenQuestionActionsBottomSheet -> flow {
                emit(
                    TestManagerStore.Effect.VisibleQuestionEditBottomSheet(
                        questionId = intent.questionId
                    )
                )
            }

            is TestManagerStore.Event.OpenNotificationAboutDeleteBottomSheet -> flow {
                emit(
                    TestManagerStore.Effect.VisibleNotificationAboutDeleteBottomSheet(
                        testId = intent.testId
                    )
                )
            }

            is TestManagerStore.Event.OpenNoQuestionsNotificationBottomSheet -> flow {
                emit(
                    TestManagerStore.Effect.VisibleNoQuestionsNotificationBottomSheet(
                        testId = intent.testId
                    )
                )
            }

            is TestManagerStore.Event.OpenSettingsBottomSheet -> flow {
                emit(
                    TestManagerStore.Effect.VisibleSettingBottomSheet(
                        testId = currentState.testId
                    )
                )
            }

            is TestManagerStore.Event.DeleteQuestion -> flow {
                withIO {
                    questionRepository.deleteQuestion(
                        questionId = intent.questionId,
                        testId = currentState.testId
                    )
                }
                AppAnalytics.deleteQuestion()
                emit(
                    TestManagerStore.Effect.DeleteQuestion(
                        questionId = intent.questionId
                    )
                )
                val testData = withIO { testRepository.getTest(testId) }
                val questions =
                    withIO { questionRepository.getQuestionsWithAnswersByTestId(testId) }
                emit(TestManagerStore.Effect.LoadScreen(testData, questions))
            }

            is TestManagerStore.Event.DeleteTest -> flow {
                withIO {
                    testRepository.deleteTest(currentState.testId)
                }
                emit(
                    TestManagerStore.Effect.DeleteTest(
                        testId = currentState.testId
                    )
                )
                AppAnalytics.deleteTest()
                sendSideEffect(TestManagerStore.SideEffect.Close)
            }

            is TestManagerStore.Event.Reload -> flow {
                val testData = withIO { testRepository.getTest(testId) }
                val questions =
                    withIO { questionRepository.getQuestionsWithAnswersByTestId(testId) }
                emit(TestManagerStore.Effect.LoadScreen(testData, questions))
            }

            is TestManagerStore.Event.ShareTest -> flow {
                val json = jsonBuilder.encodeToString(
                    TestShareData(
                        title = currentState.testData?.title ?: "",
                        questions = currentState.allQuestions.map {
                            QuestionShareData(
                                question = it.question.questionText,
                                position = it.question.position,
                                answers = it.answers.map { answer ->
                                    AnswerShareData(
                                        text = answer.text,
                                        isRightAnswer = answer.isRightAnswer
                                    )
                                }
                            )
                        },
                    )
                )
                AppAnalytics.testManagerShareTest()
                shareFile(json, "tutor_${currentState.testData?.title}.json")
            }

            is TestManagerStore.Event.StartTesting -> flow {
                if (currentState.allQuestions.isEmpty()) {
                    emit(
                        TestManagerStore.Effect.VisibleNoQuestionsNotificationBottomSheet(
                            testId = currentState.testId
                        )
                    )
                    return@flow
                }

                sendSideEffect(
                    TestManagerStore.SideEffect.StartTesting(
                        testId = currentState.testId,
                        testsListType = TestsListType.NORMAL
                    )
                )
            }

            is TestManagerStore.Event.OpenSearchField -> flow {
                emit(TestManagerStore.Effect.ToggleShowSearchField(showSearchField = true))
            }

            is TestManagerStore.Event.CloseSearchField -> flow {
                emit(TestManagerStore.Effect.ClearSearch)
                emit(TestManagerStore.Effect.ToggleShowSearchField(showSearchField = false))
            }

            is TestManagerStore.Event.SearchQueryChanged -> flow {
                val query = intent.query.trim()

                val filteredQuestions = filterQuestionsList(
                    query = query,
                    allQuestions = currentState.allQuestions
                )

                emit(
                    TestManagerStore.Effect.UpdateSearchResult(
                        query = query,
                        filteredQuestions = filteredQuestions
                    )
                )

            }
        }
    }

    private fun shareFile(json: String, fileName: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "application/json")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            resolver.openOutputStream(it)?.use { outputStream ->
                outputStream.write(json.toByteArray())
            }

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                type = "application/json"
            }

            val chooserIntent = Intent.createChooser(shareIntent, "Поделиться файлом")
            if (context !is Activity) {
                chooserIntent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            }

            context.startActivity(chooserIntent)
        }
    }

    private fun filterQuestionsList(
        query: String,
        allQuestions: List<QuestionWithAnswersData>,
    ): List<QuestionWithAnswersData> {
        val normalizedQuery = query.lowercase()
        return if (query.isEmpty()) {
            allQuestions
        } else {
            allQuestions.filter { questionWithAnswer ->

                val inQuestionText = questionWithAnswer.question.questionText
                    .lowercase()
                    .contains(normalizedQuery)

                val inAnswers = questionWithAnswer.answers.any { answer ->
                    answer.text
                        .lowercase()
                        .contains(normalizedQuery)
                }

                inQuestionText || inAnswers
            }
        }
    }
}