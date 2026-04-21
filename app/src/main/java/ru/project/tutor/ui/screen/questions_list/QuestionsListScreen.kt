package ru.project.tutor.ui.screen.questions_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.screen.questions_list.compose.QuestionsListContent
import ru.project.tutor.ui.screen.test_info.TestsListType

@Composable
fun QuestionsListScreenContent(testId: Int, testTypeList: TestsListType) {
    val rootNavigator = RootNavigation.current
    val viewModel =
        koinViewModel<QuestionsListViewModel>(parameters = { parametersOf(testId, testTypeList) })

    viewModel.sideEffect { effect ->
        when (effect) {
            is QuestionsListStore.SideEffect.Close -> {
                rootNavigator?.removeLastOrNull()
            }
        }
    }

    val state by viewModel.uiState.collectAsState()

    QuestionsListContent(state, viewModel::onEvent)
}
