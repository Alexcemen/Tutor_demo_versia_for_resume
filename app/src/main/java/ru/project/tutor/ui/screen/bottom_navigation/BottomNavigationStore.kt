package ru.project.tutor.ui.screen.bottom_navigation

import ru.project.tutor.common_ui.composable.elements.bottom_navigation.BottomNavigationItem
import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState

object BottomNavigationStore {
    data class State(
        val listNavigation: List<BottomNavigationItem>,
    ) : MviState

    data class UiState(
        val listNavigation: List<BottomNavigationItem>,
    ) : MviUiState

    sealed interface SideEffect : MviSideEffect {
        data object NavigateHome : SideEffect
        data object NavigateProfile : SideEffect
    }

    sealed interface Event : MviEvent {
        data class SelectItem(val position: Int) : Event
    }

    sealed interface Effect : MviEffect {
        data class UpdateSelected(val newList: List<BottomNavigationItem>) : Effect
    }
}