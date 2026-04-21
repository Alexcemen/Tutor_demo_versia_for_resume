package ru.project.tutor.ui.screen.starting_testing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.navigation.TestProcess
import ru.project.tutor.ui.screen.starting_testing.composable.StartingTestingContentScreen
import ru.project.tutor.ui.screen.test_info.TestsListType

@Composable
fun StartingTestingScreenContent(testId: Int, mode: TestsListType) {

    val rootNavigator = RootNavigation.current
    val nav = RootNavigation.current
    val viewModel =
        koinViewModel<StartingTestingViewModel>(parameters = { parametersOf(testId, mode) })
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(StartingTestingStore.Event.Reload)
    }

    viewModel.sideEffect { effect ->
        when (effect) {
            is StartingTestingStore.SideEffect.Close -> {
                nav?.removeLastOrNull()
            }

            is StartingTestingStore.SideEffect.OpenTestProcess -> {
                rootNavigator?.add(
                    TestProcess(
                        testMode = effect.testMode,
                        startingMode = effect.startingMode,
                        testId = effect.testId,
                        options = effect.options
                    )
                )
            }

        }
    }
    StartingTestingContentScreen(state, viewModel::onEvent)
}