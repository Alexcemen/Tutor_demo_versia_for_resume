package ru.project.tutor.ui.screen.loading

import ru.project.tutor.common_ui.composable.mvi.Reducer

class SplashReducer() : Reducer<SplashScreenStore.State, SplashScreenStore.UiState> {
    override fun reduce(state: SplashScreenStore.State): SplashScreenStore.UiState {
        return SplashScreenStore.UiState
    }
}