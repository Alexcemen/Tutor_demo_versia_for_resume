package ru.project.tutor.ui.screen.bottom_navigation

import ru.project.tutor.common_ui.composable.mvi.Reducer

class BottomNavigationReducer() :
    Reducer<BottomNavigationStore.State, BottomNavigationStore.UiState> {
    override fun reduce(state: BottomNavigationStore.State): BottomNavigationStore.UiState {
        return BottomNavigationStore.UiState(
            state.listNavigation
        )
    }
}