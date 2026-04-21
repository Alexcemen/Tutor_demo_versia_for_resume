package ru.project.tutor.ui.screen.manual_ai

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import org.koin.androidx.compose.koinViewModel
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.navigation.BottomNavigation
import ru.project.tutor.ui.navigation.TestManager
import ru.project.tutor.ui.screen.manual_ai.composable.ManualAiContent

@Composable
fun ManualAiScreen() {
    val viewModel = koinViewModel<ManualAiViewModel>()
    val rootNavigator = RootNavigation.current
    val context = LocalContext.current
    viewModel.sideEffect { effect ->
        when (effect) {
            is ManualAiStore.SideEffect.OpenTestManager -> {
                rootNavigator?.removeIf { it.type != BottomNavigation::class.simpleName }
                rootNavigator?.add(TestManager(testId = effect.testId))
            }

            is ManualAiStore.SideEffect.OpenLink -> {
                val intent = Intent(Intent.ACTION_VIEW, effect.link.toUri())
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }

            is ManualAiStore.SideEffect.Close -> {
                rootNavigator?.removeLastOrNull()
            }
        }
    }

    ManualAiContent(viewModel::onEvent)
}