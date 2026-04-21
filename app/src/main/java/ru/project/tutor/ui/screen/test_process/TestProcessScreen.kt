package ru.project.tutor.ui.screen.test_process

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.models.options_for_testing.OptionsStartTestingUi
import ru.project.tutor.ui.navigation.BottomNavigation
import ru.project.tutor.ui.navigation.TestResult
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.ui.screen.test_process.composable.TestProcessContent

@Composable
fun TestProcessScreenContent(
    testMode: TestMode,
    startingMode: TestsListType,
    testId: Int,
    options: OptionsStartTestingUi,
) {
    val rootNavigator = RootNavigation.current
    val nav = RootNavigation.current
    val viewModel = koinViewModel<TestProcessViewModel>(parameters = {
        parametersOf(
            testMode,
            startingMode,
            testId,
            options
        )
    })
    val state by viewModel.uiState.collectAsState()
    val bannerAdLoader = viewModel.bannerAdLoader

    viewModel.sideEffect { effect ->
        when (effect) {
            TestProcessStore.SideEffect.Close -> {
                nav?.removeLastOrNull()
            }

            TestProcessStore.SideEffect.CloseScreen -> {
                nav?.removeLastOrNull()
            }

            is TestProcessStore.SideEffect.OpenTestResultScreen -> {
                nav?.removeIf { it.type != BottomNavigation::class.simpleName }
                rootNavigator?.add(TestResult(testId = effect.testId, attemptId = effect.attemptId))
            }
        }
    }
    TestProcessContent(state, viewModel::onEvent, bannerAdLoader)
}