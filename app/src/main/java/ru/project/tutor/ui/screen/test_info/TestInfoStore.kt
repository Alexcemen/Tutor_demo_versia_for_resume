package ru.project.tutor.ui.screen.test_info

import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState
import ru.project.tutor.domain.models.test.TestWithCountQuestions
import ru.project.tutor.ui.models.CountQuestionsInTestUi
import ru.project.tutor.ui.models.TestShortUi
import ru.project.tutor.ui.screen.test_info.composable.models.UserStatisticsUi

object TestInfoStore {
    data class State(
        val testList: List<TestWithCountQuestions> = emptyList(),
        val currentPage: Int = 1,
        val testActionBottomSheetId: Int = -1,
        val firstLaunchDate: String = "",
        val allCountTestCreated: Int = 0,
        val allCountErrors: Int = 0,
        val allCountTestsCompleted: Int = 0,
        val allCountTime: String = "0с",
        val showTelegramBanner: Boolean = false,
        val showAdExplanationBanner: Boolean = false,
    ) : MviState

    data class UiState(
        val testList: List<TestShortUi>,
        val countQuestions: CountQuestionsInTestUi,
        val currentPage: Int,
        val isVisibleBottomSheet: Boolean,
        val userStatisticsUi: UserStatisticsUi,
        val showTelegramBanner: Boolean,
        val showAdExplanationBanner: Boolean,
    ) : MviUiState

    sealed interface SideEffect : MviSideEffect {
        data object OpenCreateNewTest : SideEffect
        data class OpenTestsList(val testsListType: TestsListType) : SideEffect
        data class OpenTestDetails(val testId: Int) : SideEffect
        data class OpenStartTesting(val testId: Int, val mode: TestsListType) : SideEffect
        data object OpenTelegram : SideEffect
        data object OpenAdExplanation : SideEffect
    }

    sealed interface Event : MviEvent {
        data object OpenCreateNewTest : Event
        data class OpenTestsList(val testsListType: TestsListType) : Event
        data class UpdatePositionState(val position: Int) : Event
        data class ClickTestCard(val id: Int) : Event
        data object CloseBottomSheet : Event
        data class ActionBottomSheet(val isStartTest: Boolean) : Event
        data object SetStatistic : Event
        data object StartRequestReview : Event
        data object ClickCreateTestEmptyTest : Event
        data object CloseTelegramBanner : Event
        data object ClickTelegramBanner : Event
        data object CloseAdExplanationBanner : Event
        data object ClickAdExplanationBanner : Event
    }

    sealed interface Effect : MviEffect {
        data class UpdateTests(val tests: List<TestWithCountQuestions>) : Effect
        data class UpdatePositionTest(val position: Int) : Effect
        data class VisibleBottomSheet(val testId: Int) : Effect
        data class InitTelegramBanner(val show: Boolean) : Effect
        data object HideTelegramBanner : Effect
        data class InitAdExplanationBanner(val show: Boolean) : Effect
        data object HideAdExplanationBanner : Effect
        data class SetStatistics(
            val firstLaunchDate: String,
            val allCountTestCreated: Int,
            val allCountErrors: Int,
            val allCountTestsCompleted: Int,
            val allCountTimeText: String,
        ) : Effect
    }
}