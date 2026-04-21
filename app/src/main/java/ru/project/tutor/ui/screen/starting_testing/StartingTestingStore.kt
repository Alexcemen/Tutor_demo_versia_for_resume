package ru.project.tutor.ui.screen.starting_testing

import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState
import ru.project.tutor.domain.models.test.TestWithQuestionsData
import ru.project.tutor.ui.models.options_for_testing.OptionsStartTestingUi
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.ui.screen.test_process.TestMode

object StartingTestingStore {
    data class State(
        val isExam: Boolean,
        val testWithQuestionsData: TestWithQuestionsData? = null,
        val testId: Int = 0,
        val optionsStartTestingUi: OptionsStartTestingUi,
        val isVisibleBottomSheet: Boolean = false,
        val bottomSheetMessage: String = "",
    ) : MviState

    data class UiState(
        val isExam: Boolean,
        val countQuestions: Int,
        val testName: String,
        val testId: Int,
        val imageId: Int,
        val colorId: Int,
        val optionsStartTestingUi: OptionsStartTestingUi,
        val isVisibleBottomSheet: Boolean,
        val bottomSheetMessage: String,
    ) : MviUiState

    sealed interface SideEffect : MviSideEffect {
        data object Close : SideEffect
        data class OpenTestProcess(
            val testMode: TestMode,
            val startingMode: TestsListType,
            val testId: Int,
            val options: OptionsStartTestingUi,
        ) : SideEffect
    }

    sealed interface Event : MviEvent {
        data object Close : Event
        data object Reload : Event
        data class ToggleExamMode(val isExam: Boolean) : Event
        data object ToggleDurationOption : Event
        data object ToggleShuffleQuestions : Event
        data object ToggleShuffleAnswers : Event
        data object ToggleTagMultipleAnswerChoice : Event
        data object ToggleRandomQuestionsOption : Event
        data object ToggleShowRightAnswer : Event
        data class UpdateDuration(val duration: Int) : Event
        data class UpdateCountRandomQuestions(val countRandomQuestions: Int) : Event
        data class ActionBottom(val isExam: Boolean) : Event
        data class ShowMessageBottomSheet(val message: String) : Event
        data object CloseBottomSheet : Event
    }

    sealed interface Effect : MviEffect {
        data class LoadScreen(
            val test: TestWithQuestionsData,
        ) : Effect

        data class ToggleExamMode(val isExam: Boolean) : Effect
        data object ToggleDurationOption : Effect
        data object ToggleShuffleQuestions : Effect
        data object ToggleShuffleAnswers : Effect
        data object ToggleTagMultipleAnswerChoice : Effect
        data object ToggleRandomQuestionsOption : Effect
        data object ToggleShowRightAnswer : Effect
        data class UpdateDuration(val duration: Int) : Effect
        data class UpdateCountRandomQuestions(val countRandomQuestions: Int) : Effect
        data class VisibleBottomSheet(val testId: Int, val message: String = "") : Effect
        data object UpdateOptionsForExamMode : Effect
        data object UpdateOptionsForWorkoutMode : Effect
    }
}