package ru.project.tutor.ui.screen.ad_explanation

import ru.project.tutor.common_ui.composable.mvi.Reducer

class AdExplanationReducer : Reducer<AdExplanationStore.State, AdExplanationStore.UiState> {
    override fun reduce(state: AdExplanationStore.State): AdExplanationStore.UiState {
        return AdExplanationStore.UiState(
            telegramLink = state.telegramLink,
        )
    }
}
