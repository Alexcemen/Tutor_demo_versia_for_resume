package ru.project.tutor.ui.screen.test_factory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import org.koin.androidx.compose.koinViewModel
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.navigation.ManualAi
import ru.project.tutor.ui.navigation.TestManager
import ru.project.tutor.ui.screen.test_factory.composable.TestFactoryContent
import ru.project.tutor.utils.showError

@Composable
fun TestFactoryScreenContent() {
    val rootNavigator = RootNavigation.current
    val viewModel = koinViewModel<TestFactoryViewModel>()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    viewModel.sideEffect { effect ->
        when (effect) {
            is TestFactoryStore.SideEffect.Close -> {
                keyboardController?.hide()
                rootNavigator?.removeLastOrNull()
            }

            is TestFactoryStore.SideEffect.OpenTestManager -> {
                rootNavigator?.removeLastOrNull()
                rootNavigator?.add(TestManager(testId = effect.testId))
            }

            is TestFactoryStore.SideEffect.ShowEmptyDataError -> {
                context.showError(context.getString(R.string.empty_data_error))
            }

            TestFactoryStore.SideEffect.OpenManualAi -> {
                keyboardController?.hide()
                rootNavigator?.add(ManualAi())
            }
        }
    }
    val state by viewModel.uiState.collectAsState()

    TestFactoryContent(state, viewModel::onEvent)
}