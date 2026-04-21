package ru.project.tutor.ui.screen.question_factory

import ru.project.tutor.common_ui.composable.mvi.Reducer
import ru.project.tutor.ui.models.AnswerChoiceUi

class QuestionFactoryReducer() : Reducer<QuestionFactoryStore.State, QuestionFactoryStore.UiState> {
    override fun reduce(state: QuestionFactoryStore.State): QuestionFactoryStore.UiState {
        return QuestionFactoryStore.UiState(
            questionId = state.questionId,
            question = state.question,
            testId = state.testId,
            answerChoices = state.answerChoice.map {
                AnswerChoiceUi(
                    id = it.id,
                    text = it.text,
                    isRightAnswer = it.isRightAnswer
                )
            },
            title = state.title,
            isVisibleBottomSheet = state.isVisibleBottomSheet,
            bottomSheetMessage = state.bottomSheetMessage,
            isMultipleAnswerChoice = state.isMultipleAnswerChoice
        )
    }
}