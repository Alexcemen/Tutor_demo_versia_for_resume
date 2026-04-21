package ru.project.tutor.ui.screen.question_factory

import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState
import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.ui.models.AnswerChoiceUi

object QuestionFactoryStore {
    data class State(
        val testId: Int = -1,
        val questionId: Int = -1,
        val question: String = "",
        val isMultipleAnswerChoice: Boolean = false,
        val answerChoice: List<AnswerChoiceData> = emptyList(),
        val isVisibleBottomSheet: Boolean = false,
        val bottomSheetMessage: String = "",
        val title: String = "",
    ) : MviState

    data class UiState(
        val testId: Int,
        val questionId: Int,
        val question: String,
        val answerChoices: List<AnswerChoiceUi>,
        val isVisibleBottomSheet: Boolean,
        val bottomSheetMessage: String,
        val title: String = "",
        val isMultipleAnswerChoice: Boolean = false,
    ) : MviUiState


    sealed interface SideEffect : MviSideEffect {
        data object OpenTestManager : SideEffect
        data object Close : SideEffect
    }

    sealed interface Event : MviEvent {
        data object Save : Event
        data object Close : Event
        data object Back : Event

        data class SelectRightAnswer(val answerId: Int) : Event
        data class ShowMessageBottomSheet(val message: String) : Event
        data class UpdateQuestion(val question: String) : Event
        data class UpdateAnswer(val id: Int, val answer: String) : Event
        data class ToggleCorrect(val id: Int) : Event
        data object AddAnswer : Event
        data class RemoveAnswer(val index: Int) : Event

        data object CloseBottomSheet : Event
        data class LoadQuestion(val questionId: Int) : Event
        data object CreateNewQuestion : Event
    }

    sealed interface Effect : MviEffect {
        data class UpdateText(val question: String) : Effect
        data class LoadScreen(val testId: Int) : Effect
        data class UpdateAnswerList(val answers: List<AnswerChoiceData>) : Effect
        data class VisibleBottomSheet(val testId: Int, val message: String = "") : Effect
        data class UpdateQuestionId(val questionId: Int) : Effect
        data class SetTitle(val title: String) : Effect
        data class ToggleMultipleAnswerChoice(val isMultipleAnswerChoice: Boolean) : Effect
    }
}