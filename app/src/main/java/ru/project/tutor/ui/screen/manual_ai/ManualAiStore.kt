package ru.project.tutor.ui.screen.manual_ai

import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState

object ManualAiStore {
    object State : MviState
    object UiState : MviUiState
    sealed interface SideEffect : MviSideEffect {
        data class OpenTestManager(val testId: Int) : SideEffect
        data class OpenLink(val link: String) : SideEffect
        data object Close : SideEffect
    }

    sealed interface Event : MviEvent {
        data object ClickCopyPromtFile : Event
        data object ClickCopyPromtUrl : Event
        data object ClickDeepSeek : Event
        data object ClickInsertBuffer : Event
        data object ClickChooseFile : Event
        data object ClickClose : Event
        data object ClickOpenMovie : Event
    }

    sealed interface Effect : MviEffect
}