package ru.project.tutor.ui.screen.tests_list.tests_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.navigation.QuestionsList
import ru.project.tutor.ui.navigation.StartingTesting
import ru.project.tutor.ui.navigation.TestFactory
import ru.project.tutor.ui.navigation.TestManager
import ru.project.tutor.ui.navigation.TestsList
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.ui.screen.tests_list.tests_list.compose.TestsListContent

@Composable
fun TestsListScreenContent(testsListType: TestsListType) {
    val rootNavigator = RootNavigation.current
    val viewModel = koinViewModel<TestsListViewModel>(parameters = { parametersOf(testsListType) })

    viewModel.sideEffect { effect ->
        when (effect) {
            is TestsListStore.SideEffect.Close -> {
                rootNavigator?.removeLastOrNull()
            }

            is TestsListStore.SideEffect.OpenStartTesting -> {
                rootNavigator?.add(StartingTesting(testId = effect.testId, mode = effect.mode))
            }

            is TestsListStore.SideEffect.OpenQuestionsList -> {
                when (effect.testsListType) {
                    TestsListType.NORMAL -> rootNavigator?.add(TestManager(testId = effect.testId))
                    else -> rootNavigator?.add(
                        QuestionsList(
                            testId = effect.testId,
                            testsListType = effect.testsListType
                        )
                    )
                }
            }

            is TestsListStore.SideEffect.OpenTestList -> {
                rootNavigator?.removeLastOrNull()
                rootNavigator?.add(TestsList(testsListType = TestsListType.NORMAL))
            }

            is TestsListStore.SideEffect.OpenTestFactory -> {
                rootNavigator?.add(TestFactory())
            }
        }
    }

    val state by viewModel.uiState.collectAsState()

    TestsListContent(state, viewModel::onEvent)
}