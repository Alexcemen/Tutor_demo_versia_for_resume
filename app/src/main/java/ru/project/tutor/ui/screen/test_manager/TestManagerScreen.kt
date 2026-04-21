package ru.project.tutor.ui.screen.test_manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.navigation.QuestionFactory
import ru.project.tutor.ui.navigation.StartingTesting
import ru.project.tutor.ui.screen.test_manager.composable.TestManagerContent

@Composable
fun TestManagerScreenContent(testId: Int) {
    val nav = RootNavigation.current
    val viewModel = koinViewModel<TestManagerViewModel>(parameters = { parametersOf(testId) })
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(TestManagerStore.Event.Reload)
    }

    viewModel.sideEffect { effect ->
        when (effect) {
            TestManagerStore.SideEffect.Close -> {
                nav?.removeLastOrNull()
            }

            is TestManagerStore.SideEffect.OpenQuestionFactory -> {
                nav?.add(
                    QuestionFactory(testId = effect.testId, questionId = effect.questionId)
                )
            }

            is TestManagerStore.SideEffect.StartTesting -> {
                nav?.add(StartingTesting(testId = effect.testId, mode = effect.testsListType))
            }
        }
    }
    TestManagerContent(state, viewModel::onEvent)
}
