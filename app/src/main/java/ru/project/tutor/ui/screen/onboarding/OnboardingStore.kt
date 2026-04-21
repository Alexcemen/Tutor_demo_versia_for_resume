package ru.project.tutor.ui.screen.onboarding

import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState

object OnboardingStore {
    data class OnboardingPage(
        val emoji: String,
        val title: String,
        val description: String,
    )

    data class State(
        val currentPage: Int = 0,
    ) : MviState

    data class UiState(
        val currentPage: Int,
        val totalPages: Int,
        val isLastPage: Boolean,
    ) : MviUiState

    sealed interface SideEffect : MviSideEffect {
        data object NavigateToHome : SideEffect
    }

    sealed interface Event : MviEvent {
        data object NextPage : Event
        data object Skip : Event
        data class PageChanged(val index: Int) : Event
    }

    sealed interface Effect : MviEffect {
        data class GoToPage(val index: Int) : Effect
        data object CompleteOnboarding : Effect
    }
}
