package ru.project.tutor.ui.screen.test_result

import ru.project.tutor.common_ui.composable.mvi.Reducer


class TestResultReducer() :
    Reducer<TestResultStore.State, TestResultStore.UiState> {
    override fun reduce(state: TestResultStore.State): TestResultStore.UiState {
        return TestResultStore.UiState(
            questions = state.questions,
            isErrorDialogVisible = state.isErrorDialogVisible,
            isDontShowAgainCheckbox = state.isDontShowAgainCheckbox,
            answerFilter = state.answerFilter
        )
    }
}
