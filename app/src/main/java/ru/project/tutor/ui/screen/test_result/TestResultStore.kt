package ru.project.tutor.ui.screen.test_result

import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState
import ru.project.tutor.ui.models.QuestionWorkoutUi
import ru.project.tutor.ui.screen.test_info.TestsListType

object TestResultStore {
    enum class AnswerFilter {
        ALL, CORRECT, INCORRECT
    }

    data class State(
        val questions: List<QuestionWorkoutUi> = emptyList(),
        val allQuestions: List<QuestionWorkoutUi> = emptyList(),
        val isErrorDialogVisible: Boolean = false,
        val isDontShowAgainCheckbox: Boolean = false,
        val answerFilter: AnswerFilter = AnswerFilter.ALL,
    ) : MviState

    data class UiState(
        val questions: List<QuestionWorkoutUi> = emptyList(),
        val isErrorDialogVisible: Boolean,
        val isDontShowAgainCheckbox: Boolean,
        val answerFilter: AnswerFilter,
    ) : MviUiState

    sealed interface SideEffect : MviSideEffect {
        data object Close : SideEffect
        data class OpenStartTesting(val testId: Int, val mode: TestsListType) : SideEffect
        data class OpenErrorsList(val testId: Int, val mode: TestsListType) : SideEffect
    }

    sealed interface Event : MviEvent {
        data object Close : Event
        data object Init : Event
        data object CloseTooManyErrorsDialog : Event
        data object ToggleDontShowAgainCheckbox : Event
        data object ShowErrorsList : Event
        data object StartTesting : Event
        data class SelectAnswerFilter(val filter: AnswerFilter) : Event
    }

    sealed interface Effect : MviEffect {
        data class LoadScreen(
            val questions: List<QuestionWorkoutUi>,
        ) : Effect
        data object ShowTooManyErrorsDialog : Effect
        data object ToggleDontShowAgainCheckbox : Effect
        data object CloseTooManyErrorsDialog : Effect
        data class UpdateIsDontShowTooErrorsDialogAgain(val isDontShowTooErrorsDialogAgain: Boolean) :
            Effect
        data class UpdateAnswerFilter(val filter: AnswerFilter) : Effect
    }
}