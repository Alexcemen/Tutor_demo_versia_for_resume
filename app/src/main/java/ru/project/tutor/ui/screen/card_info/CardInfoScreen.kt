package ru.project.tutor.ui.screen.card_info

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import org.koin.androidx.compose.koinViewModel
import ru.project.tutor.common_ui.composable.mvi.sideEffect
import ru.project.tutor.ui.RootNavigation
import ru.project.tutor.ui.navigation.AdExplanation
import ru.project.tutor.ui.screen.card_info.composable.CardInfoContent

@Composable
fun CardInfoScreenContent() {
    val viewModel = koinViewModel<CardInfoViewModel>()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val rootNavigator = RootNavigation.current
    viewModel.sideEffect { effect ->
        when (effect) {
            is CardInfoStore.SideEffect.OpenLink -> {
                val intent = Intent(Intent.ACTION_VIEW, effect.url.toUri())
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }

            is CardInfoStore.SideEffect.OpenAdExplanation -> {
                rootNavigator?.add(AdExplanation())
            }

            is CardInfoStore.SideEffect.OpenReview -> {
                val appPackage = context.packageName
                val marketUri = Uri.parse("market://details?id=$appPackage")
                val webUri = Uri.parse("https://play.google.com/store/apps/details?id=$appPackage")
                val marketIntent = Intent(Intent.ACTION_VIEW, marketUri).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    setPackage("com.android.vending")
                }
                try {
                    context.startActivity(marketIntent)
                } catch (e: ActivityNotFoundException) {
                    val webIntent = Intent(Intent.ACTION_VIEW, webUri).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(webIntent)
                }
            }
        }
    }

    CardInfoContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}