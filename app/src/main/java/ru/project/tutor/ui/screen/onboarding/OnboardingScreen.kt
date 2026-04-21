package ru.project.tutor.ui.screen.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.navigation.BottomNavigation
import ru.project.tutor.ui.screen.onboarding.composable.OnboardingContent

@Composable
fun OnboardingScreen() {
    val nav = RootNavigation.current
    val viewModel = koinViewModel<OnboardingViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    viewModel.sideEffect { effect ->
        when (effect) {
            is OnboardingStore.SideEffect.NavigateToHome -> {
                nav?.removeIf { it !is BottomNavigation }
                nav?.add(BottomNavigation())
            }
        }
    }

    OnboardingContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
    )
}
