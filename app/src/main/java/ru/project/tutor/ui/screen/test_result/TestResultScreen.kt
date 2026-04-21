package ru.project.tutor.ui.screen.test_result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.navigation.BottomNavigation
import ru.project.tutor.ui.navigation.QuestionsList
import ru.project.tutor.ui.navigation.StartingTesting
import ru.project.tutor.ui.screen.test_result.compose.TestResultContent

@Composable
fun TestResultScreenContent(testId: Int, attemptId: Int) {
    val nav = RootNavigation.current
    val viewModel =
        koinViewModel<TestResultViewModel>(parameters = { parametersOf(testId, attemptId) })
    val state by viewModel.uiState.collectAsState()
    val bannerAdLoader = viewModel.bannerAdLoader

    LaunchedEffect(Unit) {
        viewModel.onEvent(TestResultStore.Event.Init)
    }

    viewModel.sideEffect { effect ->
        when (effect) {
            TestResultStore.SideEffect.Close -> {
                nav?.removeIf { it.type != BottomNavigation::class.simpleName }
            }

            is TestResultStore.SideEffect.OpenStartTesting -> {
                nav?.add(StartingTesting(testId = effect.testId, mode = effect.mode))
            }

            is TestResultStore.SideEffect.OpenErrorsList -> {
                nav?.add(QuestionsList(testId = effect.testId, testsListType = effect.mode))
            }
        }
    }
    TestResultContent(state, viewModel::onEvent, bannerAdLoader)
}
