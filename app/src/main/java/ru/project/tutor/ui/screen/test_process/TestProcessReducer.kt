package ru.project.tutor.ui.screen.test_process

import ru.project.tutor.common_ui.composable.mvi.Reducer

class TestProcessReducer() :
    Reducer<TestProcessStore.State, TestProcessStore.UiState> {
    override fun reduce(state: TestProcessStore.State): TestProcessStore.UiState = when (state) {
        is TestProcessStore.WorkoutState -> TestProcessStore.UiState(
            numberSelectedQuestion = state.numberSelectedQuestion,
            questions = state.questions,
            showRightAnswerOption = state.showRightAnswerOption,
            durationTesting = state.durationTesting,
            tagMultipleAnswerChoice = state.doNotMarkMultipleAnswerChoice,
            showFavorite = true,
            isClickableIconQuestion = true,
            showConfirmExitBottomSheet = state.showConfirmExitBottomSheet
        )

        is TestProcessStore.ExamState -> TestProcessStore.UiState(
            numberSelectedQuestion = state.numberSelectedQuestion,
            questions = state.questions,
            durationTesting = state.durationTesting,
            showRightAnswerOption = false,
            tagMultipleAnswerChoice = true,
            showFavorite = false,
            isClickableIconQuestion = false,
            showConfirmExitBottomSheet = state.showConfirmExitBottomSheet
        )
    }
}