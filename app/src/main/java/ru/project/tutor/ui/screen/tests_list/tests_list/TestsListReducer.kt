package ru.project.tutor.ui.screen.tests_list.tests_list

import ru.project.tutor.common_ui.composable.mvi.Reducer

class TestsListReducer() :
    Reducer<TestsListStore.State, TestsListStore.UiState> {
    override fun reduce(state: TestsListStore.State): TestsListStore.UiState {
        return TestsListStore.UiState(
            testList = state.testList,
            testsListType = state.testsListType,
            isVisibleBottomSheet = state.bottomSheetId != -1,
            bottomSheetId = state.bottomSheetId,
            isVisibleNotificationBottomSheet = state.notificationBottomSheetId != -1,
            notificationBottomSheetId = state.notificationBottomSheetId,
            questionsCountMap = state.questionsCountMap,
            isLoading = state.isLoading,
        )
    }
}