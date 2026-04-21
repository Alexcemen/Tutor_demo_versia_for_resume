package ru.project.tutor.ui.screen.loading

import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState

object SplashScreenStore {
    object State : MviState

    object UiState : MviUiState

    sealed interface SideEffect : MviSideEffect {
        object NavigateToApp : SideEffect
        object NavigateToOnboarding : SideEffect
    }

    sealed class Event : MviEvent {
        data object AnimationFinish : Event()
    }

    sealed interface Effect : MviEffect
}