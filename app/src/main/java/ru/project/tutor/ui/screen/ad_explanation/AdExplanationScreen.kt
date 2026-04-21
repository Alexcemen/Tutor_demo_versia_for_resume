package ru.project.tutor.ui.screen.ad_explanation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import org.koin.androidx.compose.koinViewModel
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.screen.ad_explanation.composable.AdExplanationContent

@Composable
fun AdExplanationScreenContent() {
    val context = LocalContext.current
    val nav = RootNavigation.current
    val viewModel = koinViewModel<AdExplanationViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        // Init уже вызван в ViewModel.init
    }

    viewModel.sideEffect { effect ->
        when (effect) {
            AdExplanationStore.SideEffect.Close -> {
                nav?.removeLastOrNull()
            }

            AdExplanationStore.SideEffect.OpenTelegram -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uiState.telegramLink))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }
    }

    AdExplanationContent(uiState = uiState, onEvent = viewModel::onEvent)
}
