package ru.project.tutor.ui.screen.questions_list

import ru.project.tutor.common_ui.composable.mvi.Reducer


class QuestionsListReducer() :
    Reducer<QuestionsListStore.State, QuestionsListStore.UiState> {
    override fun reduce(state: QuestionsListStore.State): QuestionsListStore.UiState {
        return QuestionsListStore.UiState(
            questionsList = state.questionsList,
            testsListType = state.testsListType,
        )
    }
}