package ru.project.tutor.ui.screen.tests_list.tests_list

import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState
import ru.project.tutor.domain.models.test.TestData
import ru.project.tutor.ui.models.TestWithCountQuestionsUi
import ru.project.tutor.ui.screen.test_info.TestsListType

object TestsListStore {

    data class State(
        val testList: List<TestData> = emptyList(),
        val questionsCountMap: Map<Int, Int> = emptyMap(),
        val testsListType: TestsListType = TestsListType.NORMAL,
        val bottomSheetId: Int = -1,
        val isVisibleBottomSheet: Boolean = false,
        val notificationBottomSheetId: Int = -1,
        val isVisibleNotificationBottomSheet: Boolean = false,
        val isLoading: Boolean = true,
    ) : MviState

    data class UiState(
        val testList: List<TestData>,
        val questionsCountMap: Map<Int, Int>,
        val testsListType: TestsListType,
        val isVisibleBottomSheet: Boolean,
        val bottomSheetId: Int,
        val isVisibleNotificationBottomSheet: Boolean,
        val notificationBottomSheetId: Int,
        val isLoading: Boolean,
    ) : MviUiState

    sealed interface SideEffect : MviSideEffect {
        data object Close : SideEffect
        data class OpenStartTesting(val testId: Int, val mode: TestsListType) : SideEffect
        data class OpenQuestionsList(val testsListType: TestsListType, val testId: Int) : SideEffect
        data object OpenTestList : SideEffect
        data object OpenTestFactory : SideEffect
    }

    sealed interface Event : MviEvent {
        data object Close : Event
        data class OpenQuestionActionBottomSheet(val testId: Int) : Event
        data object CloseBottomSheet : Event
        data class ActionBottomSheet(val isStartTest: Boolean) : Event
        data object CreateNewTest : Event
        data object OpenTestTestList : Event
        data object CloseNotificationBottomSheet : Event
        data class ShowNotificationBottomSheet(val testId: Int) : Event
    }

    sealed interface Effect : MviEffect {
        data class UpdateTests(
            val testsListType: TestsListType,
            val testsWithQuestionsEntity: List<TestWithCountQuestionsUi>,
        ) : Effect

        data class VisibleTestEditBottomSheet(val testId: Int) : Effect
        data class VisibleNotificationBottomSheet(val testId: Int) : Effect
    }
}