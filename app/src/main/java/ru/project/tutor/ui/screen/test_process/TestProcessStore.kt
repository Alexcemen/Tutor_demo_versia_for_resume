package ru.project.tutor.ui.screen.test_process

import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState
import ru.project.tutor.ui.models.AnswerChoiceDataUi
import ru.project.tutor.ui.models.QuestionForTestProcessUi

object TestProcessStore {

    sealed interface State : MviState {
        val testId: Int
        val numberSelectedQuestion: Int
        val durationTesting: Int
        val questions: List<QuestionForTestProcessUi>
        val testingStartTime: Long
        val doNotMarkMultipleAnswerChoice: Boolean
        val countRandomQuestions: Int
        val showConfirmExitBottomSheet: Boolean
    }

    data class WorkoutState(
        override val testId: Int,
        override val numberSelectedQuestion: Int = 1,
        override val durationTesting: Int = -1,
        override val questions: List<QuestionForTestProcessUi> = emptyList(),
        override val testingStartTime: Long = System.currentTimeMillis(),
        override val doNotMarkMultipleAnswerChoice: Boolean = false,
        override val countRandomQuestions: Int = -1,
        val showRightAnswerOption: Boolean = false,
        override val showConfirmExitBottomSheet: Boolean = false,
    ) : State

    data class ExamState(
        override val testId: Int,
        override val numberSelectedQuestion: Int = 1,
        override val durationTesting: Int = -1,
        override val questions: List<QuestionForTestProcessUi> = emptyList(),
        override val testingStartTime: Long = System.currentTimeMillis(),
        override val doNotMarkMultipleAnswerChoice: Boolean = true,
        override val countRandomQuestions: Int = -1,
        override val showConfirmExitBottomSheet: Boolean = false,
    ) : State

    object StateFactory {
        fun empty(): State =
            WorkoutState(
                testId = -1,
                questions = emptyList(),
                numberSelectedQuestion = 1,
                durationTesting = -1,
                showRightAnswerOption = false
            )
    }

    data class UiState(
        val numberSelectedQuestion: Int,
        val questions: List<QuestionForTestProcessUi>,
        val showRightAnswerOption: Boolean,
        val durationTesting: Int,
        val tagMultipleAnswerChoice: Boolean,
        val showFavorite: Boolean,
        val isClickableIconQuestion: Boolean,
        val showConfirmExitBottomSheet: Boolean,
    ) : MviUiState

    sealed interface SideEffect : MviSideEffect {
        data object Close : SideEffect
        data object CloseScreen : SideEffect
        data class OpenTestResultScreen(val testId: Int, val attemptId: Int) : SideEffect
    }

    sealed interface Event : MviEvent {
        data object Close : Event
        data object Init : Event
        data class OpenIconQuestion(val questionNumber: Int) : Event
        data class ClickOnAnswerChoice(
            val selectedAnswer: AnswerChoiceDataUi,
        ) : Event

        data object CheckAnswerQuestion : Event
        data object NextQuestion : Event
        data object TimerFinished : Event
        data object ToggleFavorite : Event
        data object Finish : Event
        data object ConfirmExitBack : Event
        data object CloseConfirmExitBottomSheet : Event
        data object CloseScreen : Event
    }

    sealed interface Effect : MviEffect {
        data class LoadWorkoutScreen(
            val testId: Int,
            val questions: List<QuestionForTestProcessUi>,
            val showRightAnswerOption: Boolean,
            val durationTesting: Int,
            val doNotMarkMultipleAnswerChoice: Boolean,
        ) : Effect

        data class LoadExamScreen(
            val testId: Int,
            val questions: List<QuestionForTestProcessUi>,
            val durationTesting: Int,
        ) : Effect

        data class OpenIconQuestion(val questionNumber: Int) : Effect
        data class UpdateQuestions(
            val updatedQuestions: List<QuestionForTestProcessUi>,
        ) : Effect

        data object NextQuestion : Effect
        data class ToggleFavorite(val questionId: Int, val isFavorite: Boolean) : Effect
        data class ShowConfirmExitBottomSheet(val visible: Boolean) : Effect
    }
}