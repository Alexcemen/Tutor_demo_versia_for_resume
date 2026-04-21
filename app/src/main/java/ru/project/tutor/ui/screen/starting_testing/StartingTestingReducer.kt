package ru.project.tutor.ui.screen.starting_testing

import ru.project.tutor.common_ui.composable.mvi.Reducer

class StartingTestingReducer() :
    Reducer<StartingTestingStore.State, StartingTestingStore.UiState> {
    override fun reduce(state: StartingTestingStore.State): StartingTestingStore.UiState {
        return StartingTestingStore.UiState(
            isExam = state.isExam,
            countQuestions = state.testWithQuestionsData?.questions?.size ?: 0,
            testId = state.testWithQuestionsData?.testData?.id ?: 0,
            testName = state.testWithQuestionsData?.testData?.title ?: "",
            imageId = state.testWithQuestionsData?.testData?.imageId ?: 1,
            colorId = state.testWithQuestionsData?.testData?.colorId ?: 1,
            optionsStartTestingUi = state.optionsStartTestingUi,
            isVisibleBottomSheet = state.isVisibleBottomSheet,
            bottomSheetMessage = state.bottomSheetMessage
        )
    }
}