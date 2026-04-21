package ru.project.tutor.ui.screen.manual_ai

import ru.project.tutor.common_ui.composable.mvi.Reducer

class ManualAiReducer() :
    Reducer<ManualAiStore.State, ManualAiStore.UiState> {
    override fun reduce(state: ManualAiStore.State): ManualAiStore.UiState {
        return ManualAiStore.UiState
    }
}