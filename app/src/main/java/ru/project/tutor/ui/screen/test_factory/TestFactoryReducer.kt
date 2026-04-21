package ru.project.tutor.ui.screen.test_factory

import ru.project.tutor.common_ui.composable.mvi.Reducer

class TestFactoryReducer() : Reducer<TestFactoryStore.State, TestFactoryStore.UiState> {
    override fun reduce(state: TestFactoryStore.State): TestFactoryStore.UiState {
        return TestFactoryStore.UiState(
            nameTest = state.nameTest,
        )
    }
}