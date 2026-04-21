package ru.project.tutor.ui.screen.ad_explanation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.BottomSpacerSystem
import ru.project.tutor.common_ui.composable.elements.ContainerContent
import ru.project.tutor.common_ui.composable.elements.MainToolbar
import ru.project.tutor.common_ui.composable.elements.PrimaryButton
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.screen.ad_explanation.AdExplanationStore

@Composable
fun AdExplanationContent(
    uiState: AdExplanationStore.UiState,
    onEvent: (AdExplanationStore.Event) -> Unit,
) {
    ContainerContent(
        topBar = {
            MainToolbar(
                text = stringResource(R.string.ad_explanation_screen_title),
                isVisibleBack = true,
                onClickBack = { onEvent(AdExplanationStore.Event.Close) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.ad_explanation_greeting),
                style = AppTheme.textStyle.titleOne,
                color = AppTheme.colors.text.primary,
            )
            SpacerHeight(12.dp)
            Text(
                text = stringResource(R.string.ad_explanation_paragraph_1),
                style = AppTheme.textStyle.bodyOne,
                color = AppTheme.colors.text.placeholder,
            )
            SpacerHeight(12.dp)
            Text(
                text = stringResource(R.string.ad_explanation_paragraph_2),
                style = AppTheme.textStyle.bodyOne,
                color = AppTheme.colors.text.placeholder,
            )
            SpacerHeight(12.dp)
            Text(
                text = stringResource(R.string.ad_explanation_paragraph_3),
                style = AppTheme.textStyle.subtitleOne,
                color = AppTheme.colors.text.primary,
            )
            SpacerHeight(8.dp)
            Text(
                text = stringResource(R.string.ad_explanation_ai_integration),
                style = AppTheme.textStyle.bodyOne,
                color = AppTheme.colors.text.placeholder,
            )
            SpacerHeight(4.dp)
            Text(
                text = stringResource(R.string.ad_explanation_cloud_storage),
                style = AppTheme.textStyle.bodyOne,
                color = AppTheme.colors.text.placeholder,
            )
            SpacerHeight(4.dp)
            Text(
                text = stringResource(R.string.ad_explanation_and_more),
                style = AppTheme.textStyle.bodyOne,
                color = AppTheme.colors.text.placeholder,
            )
            SpacerHeight(12.dp)
            Text(
                text = stringResource(R.string.ad_explanation_paragraph_4),
                style = AppTheme.textStyle.bodyOne,
                color = AppTheme.colors.text.placeholder,
            )
            SpacerHeight(12.dp)
            Text(
                text = stringResource(R.string.ad_explanation_paragraph_5),
                style = AppTheme.textStyle.bodyOne,
                color = AppTheme.colors.text.placeholder,
            )
            SpacerHeight(16.dp)
            Text(
                text = stringResource(R.string.ad_explanation_telegram_header),
                style = AppTheme.textStyle.subtitleOne,
                color = AppTheme.colors.text.blue,
            )
            SpacerHeight(8.dp)
            Text(
                text = stringResource(R.string.ad_explanation_telegram_what_you_can_do),
                style = AppTheme.textStyle.bodyOne,
                color = AppTheme.colors.text.placeholder,
            )
            SpacerHeight(24.dp)
            PrimaryButton(
                text = stringResource(R.string.ad_explanation_go_to_telegram_button),
                onClick = { onEvent(AdExplanationStore.Event.OpenTelegram) },
                modifier = Modifier.fillMaxWidth()
            )
            SpacerHeight(24.dp)
            Text(
                text = stringResource(R.string.ad_explanation_finish),
                style = AppTheme.textStyle.bodyOne,
                color = AppTheme.colors.text.placeholder,
            )
            SpacerHeight(24.dp)
            PrimaryButton(
                text = stringResource(R.string.ad_explanation_close_button),
                onClick = { onEvent(AdExplanationStore.Event.Close) },
                modifier = Modifier.fillMaxWidth()
            )
            BottomSpacerSystem()
        }
    }
}
