package ru.project.tutor.ui.screen.test_factory

import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState

object TestFactoryStore {
    data class State(
        val nameTest: String = "",
    ) : MviState

    data class UiState(
        val nameTest: String,
    ) : MviUiState

    sealed interface SideEffect : MviSideEffect {
        data object Close : SideEffect
        data class OpenTestManager(val testId: Int) : SideEffect
        data object ShowEmptyDataError : SideEffect
        data object OpenManualAi : SideEffect
    }

    sealed interface Event : MviEvent {
        data object Close : Event
        data object Save : Event
        data object OpenFileManager : Event
        data object ClickManualAi : Event
        data class UpdateText(val text: String) : Event
    }

    sealed interface Effect : MviEffect {
        data class UpdateText(val nameTest: String) : Effect
    }
}