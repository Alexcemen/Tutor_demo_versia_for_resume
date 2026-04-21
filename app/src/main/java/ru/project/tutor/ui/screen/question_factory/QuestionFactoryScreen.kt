package ru.project.tutor.ui.screen.question_factory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.screen.question_factory.composable.QuestionFactoryContentScreen

@Composable
fun QuestionFactoryScreenContent(testId: Int, questionId: Int) {
    val rootNavigator = RootNavigation.current
    val viewModel =
        koinViewModel<QuestionFactoryViewModel>(parameters = { parametersOf(testId, questionId) })
    val state by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    viewModel.sideEffect { effect ->
        when (effect) {
            is QuestionFactoryStore.SideEffect.Close -> {
                keyboardController?.hide()
                rootNavigator?.removeLastOrNull()
            }

            is QuestionFactoryStore.SideEffect.OpenTestManager -> {
                rootNavigator?.removeLastOrNull()
            }
        }
    }

    QuestionFactoryContentScreen(state, viewModel::onEvent)
}