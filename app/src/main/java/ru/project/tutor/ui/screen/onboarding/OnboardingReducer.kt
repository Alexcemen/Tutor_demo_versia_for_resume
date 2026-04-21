package ru.project.tutor.ui.screen.onboarding

import ru.project.tutor.common_ui.composable.mvi.Reducer

private const val TOTAL_PAGES = 3

class OnboardingReducer() :
    Reducer<OnboardingStore.State, OnboardingStore.UiState> {
    override fun reduce(state: OnboardingStore.State): OnboardingStore.UiState {
        return OnboardingStore.UiState(
            currentPage = state.currentPage,
            totalPages = TOTAL_PAGES,
            isLastPage = state.currentPage == TOTAL_PAGES - 1,
        )
    }
}
