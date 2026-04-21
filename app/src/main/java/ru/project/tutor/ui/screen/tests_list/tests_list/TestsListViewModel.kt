package ru.project.tutor.ui.screen.tests_list.tests_list

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.domain.models.test.TestData
import ru.project.tutor.domain.repository.TestRepository
import ru.project.tutor.ui.models.TestWithCountQuestionsUi
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.utils.withIO

class TestsListViewModel constructor(
    reducer: TestsListReducer,
    private val testRepository: TestRepository,
    private val testsListType: TestsListType,
) : ScreenViewModel<TestsListStore.State, TestsListStore.Event, TestsListStore.SideEffect, TestsListStore.Effect, TestsListStore.UiState>(
    reducer
) {

    init {
        viewModelScope.launch {
            testRepository.getTestsWithCounts().collect { allTestsWithQuestions ->

                val updatedTests = withIO {
                    allTestsWithQuestions.map { testWithQuestions ->

                        val count = when (testsListType) {
                            TestsListType.NORMAL -> testWithQuestions.normalQuestionsCount
                            TestsListType.FAVORITE -> testWithQuestions.favoriteQuestionsCount
                            TestsListType.ERRORS -> testWithQuestions.errorQuestionsCount
                        }

                        TestWithCountQuestionsUi(
                            testData = TestData(
                                id = testWithQuestions.testData.id,
                                title = testWithQuestions.testData.title,
                                colorId = testWithQuestions.testData.colorId,
                                imageId = testWithQuestions.testData.imageId,
                                dateLastTake = testWithQuestions.testData.dateLastTake,
                                dateCreation = testWithQuestions.testData.dateCreation
                            ),
                            countQuestions = count
                        )
                    }
                }

                forceEffect(
                    TestsListStore.Effect.UpdateTests(
                        testsListType = testsListType,
                        testsWithQuestionsEntity = updatedTests
                    )
                )
            }
        }
    }

    override fun createState(): TestsListStore.State = TestsListStore.State()

    override fun handleEvent(
        currentState: TestsListStore.State,
        intent: TestsListStore.Event,
    ): Flow<TestsListStore.Effect> = when (intent) {
        TestsListStore.Event.Close -> flow {
            sendSideEffect(TestsListStore.SideEffect.Close)
        }

        is TestsListStore.Event.OpenQuestionActionBottomSheet -> flow {
            emit(
                TestsListStore.Effect.VisibleTestEditBottomSheet(
                    testId = intent.testId
                )
            )
        }

        is TestsListStore.Event.CloseBottomSheet -> flow {
            if (currentState.bottomSheetId == -1) return@flow
            emit(
                TestsListStore.Effect.VisibleTestEditBottomSheet(testId = -1)
            )
        }

        is TestsListStore.Event.ActionBottomSheet -> flow {

            if (intent.isStartTest && (currentState.questionsCountMap[currentState.bottomSheetId]
                    ?: 0) == 0
            ) {
                emit(
                    TestsListStore.Effect.VisibleNotificationBottomSheet(
                        testId = currentState.bottomSheetId
                    )
                )
                return@flow
            }

            emit(
                TestsListStore.Effect.VisibleTestEditBottomSheet(testId = -1)
            )

            if (intent.isStartTest) {
                sendSideEffect(
                    TestsListStore.SideEffect.OpenStartTesting(
                        testId = currentState.bottomSheetId,
                        mode = currentState.testsListType
                    )
                )
            } else {
                sendSideEffect(
                    TestsListStore.SideEffect.OpenQuestionsList(
                        testsListType = currentState.testsListType,
                        testId = currentState.bottomSheetId
                    )
                )
            }
        }

        is TestsListStore.Event.CloseNotificationBottomSheet -> flow {
            if (currentState.notificationBottomSheetId == -1) return@flow
            emit(
                TestsListStore.Effect.VisibleNotificationBottomSheet(testId = -1)
            )
        }

        is TestsListStore.Event.ShowNotificationBottomSheet -> flow {
            emit(
                TestsListStore.Effect.VisibleNotificationBottomSheet(
                    testId = intent.testId
                )
            )
        }

        is TestsListStore.Event.CreateNewTest -> flow {
            sendSideEffect(TestsListStore.SideEffect.OpenTestFactory)
        }

        is TestsListStore.Event.OpenTestTestList -> flow {
            sendSideEffect(TestsListStore.SideEffect.OpenTestList)
        }
    }


    override fun handleEffect(
        currentState: TestsListStore.State,
        effect: TestsListStore.Effect,
    ): TestsListStore.State {
        return when (effect) {

            is TestsListStore.Effect.UpdateTests -> {
                val tests = effect.testsWithQuestionsEntity

                val questionsCountMap = tests.associate { testWithQuestions ->
                    testWithQuestions.testData.id to testWithQuestions.countQuestions
                }

                currentState.copy(
                    testList = tests.map { testWithQuestions ->
                        TestData(
                            id = testWithQuestions.testData.id,
                            title = testWithQuestions.testData.title,
                            colorId = testWithQuestions.testData.colorId,
                            imageId = testWithQuestions.testData.imageId,
                            dateLastTake = testWithQuestions.testData.dateLastTake,
                            dateCreation = testWithQuestions.testData.dateCreation
                        )
                    },
                    questionsCountMap = questionsCountMap,
                    testsListType = effect.testsListType,
                    isLoading = false,
                )
            }

            is TestsListStore.Effect.VisibleTestEditBottomSheet ->
                currentState.copy(
                    isVisibleBottomSheet = effect.testId != -1,
                    bottomSheetId = effect.testId
                )

            is TestsListStore.Effect.VisibleNotificationBottomSheet ->
                currentState.copy(
                    isVisibleNotificationBottomSheet = effect.testId != -1,
                    notificationBottomSheetId = effect.testId
                )

        }
    }
}