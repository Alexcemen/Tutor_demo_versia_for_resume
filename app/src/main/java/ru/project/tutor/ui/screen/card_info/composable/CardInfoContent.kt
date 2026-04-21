package ru.project.tutor.ui.screen.card_info.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.ContainerContent
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.elements.TopBarSpacer
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.domain.models.Language
import ru.project.tutor.ui.screen.card_info.CardInfoStore
import ru.project.tutor.ui.screen.card_info.models.CardInfoStatisticsUi

@Preview
@Composable
fun CardInfoContentPreview() {
    CardInfoContent(
        state = CardInfoStore.UiState(
            shortStatistics = CardInfoStatisticsUi(
                countCardBlocks = 2,
                timeSpendSeconds = 12,
                countTrue = 3,
                countFalse = 4
            ),
            notificationsEnabled = true,
            settingsWithNotificationOpen = true,
            selectedLanguage = Language(Language.CODE_SYSTEM),
            availableLanguages = Language.supportedLanguages,
            showLanguageSetting = true
        ),
        {}
    )
}

@Composable
fun CardInfoContent(
    state: CardInfoStore.UiState,
    onEvent: (CardInfoStore.Event) -> Unit,
) {
    ContainerContent(applyHorizontalPadding = true) {
        TopBarSpacer()
        Column(
            Modifier.verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.card_info_title),
                style = AppTheme.textStyle.largeTitleOne,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
            SpacerHeight(16.dp)
            AppVersionInfo()
            SpacerHeight(24.dp)
            TableCardInfoFeedback(
                onEvent = onEvent
            )
            SpacerHeight(24.dp)
            TableCardInfo(
                notificationsEnabled = state.notificationsEnabled,
                settingsWithNotificationOpen = state.settingsWithNotificationOpen,
                selectedLanguage = state.selectedLanguage,
                availableLanguages = state.availableLanguages,
                showLanguageSetting = state.showLanguageSetting,
                onEvent = onEvent
            )
            SpacerHeight(8.dp)
        }
    }
}

