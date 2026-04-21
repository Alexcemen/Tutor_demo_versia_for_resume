package ru.project.tutor.ui.screen.test_info

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import org.koin.androidx.compose.koinViewModel
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.navigation.AdExplanation
import ru.project.tutor.ui.navigation.StartingTesting
import ru.project.tutor.ui.navigation.TestFactory
import ru.project.tutor.ui.navigation.TestManager
import ru.project.tutor.ui.navigation.TestsList
import ru.project.tutor.ui.screen.test_info.composable.TestInfoContent
import ru.project.tutor.utils.UrlConst

@Composable
fun TestInfoScreenContent() {
    val rootNavigator = RootNavigation.current
    val viewModel = koinViewModel<TestInfoViewModel>()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(TestInfoStore.Event.SetStatistic)
        viewModel.onEvent(TestInfoStore.Event.StartRequestReview)
    }

    viewModel.sideEffect { effect ->
        when (effect) {
            is TestInfoStore.SideEffect.OpenCreateNewTest -> {
                rootNavigator?.add(TestFactory())
            }

            is TestInfoStore.SideEffect.OpenTestsList -> {
                rootNavigator?.add(TestsList(testsListType = effect.testsListType))
            }

            is TestInfoStore.SideEffect.OpenTestDetails -> {
                rootNavigator?.add(TestManager(testId = effect.testId))
            }

            is TestInfoStore.SideEffect.OpenStartTesting -> {
                rootNavigator?.add(StartingTesting(testId = effect.testId, mode = effect.mode))
            }

            is TestInfoStore.SideEffect.OpenTelegram -> {
                val intent = Intent(Intent.ACTION_VIEW, UrlConst.TELEGRAM.toUri())
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }

            is TestInfoStore.SideEffect.OpenAdExplanation -> {
                rootNavigator?.add(AdExplanation())
            }
        }
    }

    TestInfoContent(state, viewModel::onEvent)
}