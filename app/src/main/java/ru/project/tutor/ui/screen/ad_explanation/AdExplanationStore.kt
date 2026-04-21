package ru.project.tutor.ui.screen.ad_explanation

import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState

object AdExplanationStore {
    data class State(
        val telegramLink: String = "",
    ) : MviState

    data class UiState(
        val telegramLink: String,
    ) : MviUiState

    sealed interface SideEffect : MviSideEffect {
        data object Close : SideEffect
        data object OpenTelegram : SideEffect
    }

    sealed interface Event : MviEvent {
        data object Close : Event
        data object OpenTelegram : Event
    }

    sealed interface Effect : MviEffect {
        data class InitTelegramLink(val link: String) : Effect
    }
}
