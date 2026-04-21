package ru.project.tutor.ui.screen.test_info

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.project.tutor.analytics.AppAnalytics
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.common_ui.composable.utils.formatTime
import ru.project.tutor.domain.repository.AppSharedPreferences
import ru.project.tutor.domain.repository.TestRepository
import ru.project.tutor.domain.usecases.GetAllCountErrorsUseCase
import ru.project.tutor.domain.usecases.GetAllCountTestCreatedUseCase
import ru.project.tutor.domain.usecases.GetAllCountTestsCompletedUseCase
import ru.project.tutor.domain.usecases.GetAllCountTimeUseCase
import ru.project.tutor.domain.usecases.GetFirstLaunchDateUseCase
import ru.project.tutor.utils.ReviewStarterSharedFlow

class TestInfoViewModel(
    reducer: TestInfoReducer,
    private val testRepository: TestRepository,
    private val getFirstLaunchDateUseCase: GetFirstLaunchDateUseCase,
    private val getAllCountTestCreatedUseCase: GetAllCountTestCreatedUseCase,
    private val getAllCountErrorsUseCase: GetAllCountErrorsUseCase,
    private val getAllCountTestsCompletedUseCase: GetAllCountTestsCompletedUseCase,
    private val getAllCountTimeUseCase: GetAllCountTimeUseCase,
    private val reviewStarterSharedFlow: ReviewStarterSharedFlow,
    private val appSharedPreferences: AppSharedPreferences,
) : ScreenViewModel<TestInfoStore.State, TestInfoStore.Event, TestInfoStore.SideEffect, TestInfoStore.Effect, TestInfoStore.UiState>(
    reducer
) {
    companion object {
        private const val MIN_COUNT_TEST_COMPLETED_FOR_REVIEW = 10
    }

    init {
        viewModelScope.launch {
            testRepository.getTestsWithCounts().collect { allTests ->
                val sortedList = allTests.sortedByDescending { it.testData.dateLastTake }
                forceEffect(TestInfoStore.Effect.UpdateTests(tests = sortedList))
            }
        }
        val showBanner = !appSharedPreferences.isTelegramBannerShown
        forceEffect(TestInfoStore.Effect.InitTelegramBanner(showBanner))
        val showAdBanner = !appSharedPreferences.isAdExplanationBannerShown
        forceEffect(TestInfoStore.Effect.InitAdExplanationBanner(showAdBanner))
    }

    override fun createState(): TestInfoStore.State = TestInfoStore.State()

    override fun handleEffect(
        currentState: TestInfoStore.State,
        effect: TestInfoStore.Effect,
    ): TestInfoStore.State {
        return when (effect) {

            is TestInfoStore.Effect.UpdateTests -> currentState.copy(testList = effect.tests)

            is TestInfoStore.Effect.UpdatePositionTest -> currentState.copy(currentPage = effect.position)

            is TestInfoStore.Effect.VisibleBottomSheet -> currentState.copy(testActionBottomSheetId = effect.testId)

            is TestInfoStore.Effect.SetStatistics -> currentState.copy(
                firstLaunchDate = effect.firstLaunchDate,
                allCountTestCreated = effect.allCountTestCreated,
                allCountErrors = effect.allCountErrors,
                allCountTestsCompleted = effect.allCountTestsCompleted,
                allCountTime = effect.allCountTimeText
            )

            is TestInfoStore.Effect.InitTelegramBanner -> {
                currentState.copy(showTelegramBanner = effect.show)
            }

            is TestInfoStore.Effect.HideTelegramBanner -> {
                appSharedPreferences.isTelegramBannerShown = true
                currentState.copy(showTelegramBanner = false)
            }

            is TestInfoStore.Effect.InitAdExplanationBanner -> {
                currentState.copy(showAdExplanationBanner = effect.show)
            }

            is TestInfoStore.Effect.HideAdExplanationBanner -> {
                appSharedPreferences.isAdExplanationBannerShown = true
                currentState.copy(showAdExplanationBanner = false)
            }
        }
    }

    override fun handleEvent(
        currentState: TestInfoStore.State,
        intent: TestInfoStore.Event,
    ): Flow<TestInfoStore.Effect> = when (intent) {

        TestInfoStore.Event.OpenCreateNewTest -> flow {
            AppAnalytics.openCreateNewTest()
            sendSideEffect(TestInfoStore.SideEffect.OpenCreateNewTest)
        }

        is TestInfoStore.Event.OpenTestsList -> flow {
            AppAnalytics.clickTestList(intent.testsListType.name.lowercase())
            sendSideEffect(TestInfoStore.SideEffect.OpenTestsList(intent.testsListType))
        }

        is TestInfoStore.Event.UpdatePositionState -> flow {
            emit(TestInfoStore.Effect.UpdatePositionTest(position = intent.position))
        }

        is TestInfoStore.Event.ClickTestCard -> flow {
            if (currentState.testActionBottomSheetId != -1) return@flow
            emit(
                TestInfoStore.Effect.VisibleBottomSheet(testId = intent.id)
            )
        }

        is TestInfoStore.Event.CloseBottomSheet -> flow {
            if (currentState.testActionBottomSheetId == -1) return@flow
            emit(
                TestInfoStore.Effect.VisibleBottomSheet(testId = -1)
            )
        }

        is TestInfoStore.Event.ActionBottomSheet -> flow {
            emit(
                TestInfoStore.Effect.VisibleBottomSheet(testId = -1)
            )
            if (intent.isStartTest) {
                sendSideEffect(
                    TestInfoStore.SideEffect.OpenStartTesting(
                        currentState.testActionBottomSheetId,
                        TestsListType.NORMAL
                    )
                )
            } else {
                sendSideEffect(TestInfoStore.SideEffect.OpenTestDetails(currentState.testActionBottomSheetId))
            }
        }

        is TestInfoStore.Event.SetStatistic -> flow {
            val allCountTime = getAllCountTimeUseCase()
            val allCountTimeText = formatTime(allCountTime)
            emit(
                TestInfoStore.Effect.SetStatistics(
                    firstLaunchDate = getFirstLaunchDateUseCase(),
                    allCountTestCreated = getAllCountTestCreatedUseCase(),
                    allCountErrors = getAllCountErrorsUseCase(),
                    allCountTestsCompleted = getAllCountTestsCompletedUseCase(),
                    allCountTimeText = allCountTimeText
                )
            )
        }

        is TestInfoStore.Event.StartRequestReview -> flow {
            val countTestCompleted = getAllCountTestsCompletedUseCase()
            if (countTestCompleted == MIN_COUNT_TEST_COMPLETED_FOR_REVIEW) {
                reviewStarterSharedFlow.start()
            }
        }

        TestInfoStore.Event.ClickCreateTestEmptyTest -> flow {
            AppAnalytics.openCreateNewTestEmptyBlock()
            sendSideEffect(TestInfoStore.SideEffect.OpenCreateNewTest)
        }

        is TestInfoStore.Event.CloseTelegramBanner -> flow {
            emit(TestInfoStore.Effect.HideTelegramBanner)
        }

        is TestInfoStore.Event.ClickTelegramBanner -> flow {
            emit(TestInfoStore.Effect.HideTelegramBanner)
            sendSideEffect(TestInfoStore.SideEffect.OpenTelegram)
        }

        is TestInfoStore.Event.CloseAdExplanationBanner -> flow {
            emit(TestInfoStore.Effect.HideAdExplanationBanner)
        }

        is TestInfoStore.Event.ClickAdExplanationBanner -> flow {
            emit(TestInfoStore.Effect.HideAdExplanationBanner)
            sendSideEffect(TestInfoStore.SideEffect.OpenAdExplanation)
        }
    }
}
