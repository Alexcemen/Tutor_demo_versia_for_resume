package ru.project.tutor.ui.screen.onboarding

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.project.tutor.analytics.AppAnalytics
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.domain.repository.AppSharedPreferences

private const val TOTAL_PAGES = 3

class OnboardingViewModel(
    reducer: OnboardingReducer,
    private val appSharedPreferences: AppSharedPreferences,
) : ScreenViewModel<OnboardingStore.State, OnboardingStore.Event, OnboardingStore.SideEffect, OnboardingStore.Effect, OnboardingStore.UiState>(
    reducer
) {

    init {
        AppAnalytics.onboardingPageShown1()
    }

    override fun createState(): OnboardingStore.State = OnboardingStore.State()

    override fun handleEvent(
        currentState: OnboardingStore.State,
        intent: OnboardingStore.Event,
    ): Flow<OnboardingStore.Effect> {
        return when (intent) {
            is OnboardingStore.Event.NextPage -> {
                val nextPage = currentState.currentPage + 1
                if (nextPage >= TOTAL_PAGES) {
                    flow {
                        AppAnalytics.onboardingComplete()
                        emit(OnboardingStore.Effect.CompleteOnboarding)
                        sendSideEffect(OnboardingStore.SideEffect.NavigateToHome)
                    }
                } else {
                    flow { emit(OnboardingStore.Effect.GoToPage(nextPage)) }
                }
            }

            is OnboardingStore.Event.Skip -> {
                flow {
                    AppAnalytics.onboardingSkip()
                    emit(OnboardingStore.Effect.CompleteOnboarding)
                    sendSideEffect(OnboardingStore.SideEffect.NavigateToHome)
                }
            }

            is OnboardingStore.Event.PageChanged -> {
                flow { emit(OnboardingStore.Effect.GoToPage(intent.index)) }
            }
        }
    }

    override fun handleEffect(
        currentState: OnboardingStore.State,
        effect: OnboardingStore.Effect,
    ): OnboardingStore.State {
        return when (effect) {
            is OnboardingStore.Effect.GoToPage -> {
                when (effect.index) {
                    1 -> AppAnalytics.onboardingPageShown2()
                    2 -> AppAnalytics.onboardingPageShown3()
                }
                currentState.copy(currentPage = effect.index)
            }
            is OnboardingStore.Effect.CompleteOnboarding -> {
                appSharedPreferences.isOnboardingShown = true
                currentState
            }
        }
    }
}
