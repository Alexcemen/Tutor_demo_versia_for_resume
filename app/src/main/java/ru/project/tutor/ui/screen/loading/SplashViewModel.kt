package ru.project.tutor.ui.screen.loading

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.project.tutor.analytics.AppAnalytics
import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.domain.usecases.GetAllCountErrorsUseCase
import ru.project.tutor.domain.usecases.GetAllCountTestCreatedUseCase
import ru.project.tutor.domain.usecases.GetAllCountTestsCompletedUseCase
import ru.project.tutor.domain.usecases.GetAllCountTimeUseCase
import ru.project.tutor.domain.usecases.GetFirstLaunchDateUseCase
import ru.project.tutor.domain.usecases.InitFirstLaunchDateUseCase

class SplashViewModel(
    reducer: SplashReducer,
    getFirstLaunchDateUseCase: GetFirstLaunchDateUseCase,
    getAllCountTestCreatedUseCase: GetAllCountTestCreatedUseCase,
    getAllCountErrorsUseCase: GetAllCountErrorsUseCase,
    getAllCountTestsCompletedUseCase: GetAllCountTestsCompletedUseCase,
    getAllCountTimeUseCase: GetAllCountTimeUseCase,
    private val initFirstLaunchDateUseCase: InitFirstLaunchDateUseCase,
    private val appSharedPreferences: ru.project.tutor.domain.repository.AppSharedPreferences,
) : ScreenViewModel<SplashScreenStore.State, SplashScreenStore.Event, SplashScreenStore.SideEffect, MviEffect, SplashScreenStore.UiState>(
    reducer
) {

    init {
        AppAnalytics.firstLaunch(getFirstLaunchDateUseCase())
        viewModelScope.launch(Dispatchers.IO) {
            initFirstLaunchDateUseCase()
        }
        AppAnalytics.userStatistics(
            firstLaunchDate = getFirstLaunchDateUseCase(),
            allCountTestCreated = getAllCountTestCreatedUseCase().toLong(),
            allCountErrors = getAllCountErrorsUseCase().toLong(),
            allCountTestsCompleted = getAllCountTestsCompletedUseCase().toLong(),
            allCountTime = getAllCountTimeUseCase()
        )
    }

    override fun createState(): SplashScreenStore.State = SplashScreenStore.State

    override fun handleEffect(
        currentState: SplashScreenStore.State,
        effect: MviEffect,
    ): SplashScreenStore.State {
        return SplashScreenStore.State
    }

    override fun handleEvent(
        currentState: SplashScreenStore.State,
        intent: SplashScreenStore.Event,
    ): Flow<MviEffect> {
        if (intent is SplashScreenStore.Event.AnimationFinish) {
            return flow {
                if (appSharedPreferences.isOnboardingShown) {
                    sendSideEffect(SplashScreenStore.SideEffect.NavigateToApp)
                } else {
                    sendSideEffect(SplashScreenStore.SideEffect.NavigateToOnboarding)
                }
            }
        }
        return emptyFlow()
    }
}