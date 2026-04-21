package ru.project.tutor.ui.screen.loading

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.navigation.BottomNavigation
import ru.project.tutor.ui.navigation.Onboarding
import ru.project.tutor.ui.screen.loading.composable.SplashContentScreen

@Composable
fun SplashScreenContent() {
    val nav = RootNavigation.current
    val viewModel = koinViewModel<SplashViewModel>()

    viewModel.sideEffect { effect ->
        when (effect) {
            is SplashScreenStore.SideEffect.NavigateToApp -> {
                nav?.removeLastOrNull()
                nav?.add(BottomNavigation())
            }
            is SplashScreenStore.SideEffect.NavigateToOnboarding -> {
                nav?.removeLastOrNull()
                nav?.add(Onboarding())
            }
        }
    }

    SplashContentScreen(onEvent = viewModel::onEvent)
}