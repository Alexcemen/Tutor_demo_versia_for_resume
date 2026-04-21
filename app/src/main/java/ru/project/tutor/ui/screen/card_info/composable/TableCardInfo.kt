package ru.project.tutor.ui.screen.card_info.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.project.tutor.BuildConfig
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.domain.models.Language
import ru.project.tutor.domain.models.Language.Companion.nameResId
import ru.project.tutor.ui.screen.card_info.CardInfoStore

@Preview
@Composable
private fun TableCardInfoPreview() {
    TableCardInfo(
        notificationsEnabled = true,
        settingsWithNotificationOpen = true,
        selectedLanguage = Language(Language.CODE_SYSTEM),
        availableLanguages = Language.supportedLanguages,
        showLanguageSetting = true,
        onEvent = {}
    )
}

@Preview
@Composable
private fun TableCardInfoFeedbackPreview() {
    TableCardInfoFeedback(
        onEvent = {}
    )
}

@Composable
fun TableCardInfoFeedback(
    onEvent: (CardInfoStore.Event) -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(AppTheme.colors.background.secondaryTwo)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TableCardInfoItem(
                title = stringResource(R.string.privacy_policy_title),
                description = stringResource(R.string.privacy_policy_description),
                onClick = {
                    onEvent(
                        CardInfoStore.Event.OpenPrivacyPolicy
                    )
                }
            )
            CastDivider()
            TableCardInfoItem(
                title = stringResource(R.string.support_title),
                description = stringResource(R.string.support_description),
                onClick = {
                    onEvent(
                        CardInfoStore.Event.OpenSupportChat
                    )
                }
            )
            CastDivider()
            TableCardInfoItem(
                title = stringResource(R.string.profile_why_ads_title),
                description = stringResource(R.string.profile_why_ads_description),
                onClick = {
                    onEvent(
                        CardInfoStore.Event.OpenAdExplanation
                    )
                }
            )
            CastDivider()
            TableCardInfoItem(
                title = stringResource(R.string.profile_review_title),
                description = stringResource(R.string.profile_review_description),
                onClick = {
                    onEvent(
                        CardInfoStore.Event.OpenReview
                    )
                }
            )
        }
    }
}

@Composable
fun TableCardInfo(
    notificationsEnabled: Boolean,
    settingsWithNotificationOpen: Boolean,
    selectedLanguage: Language,
    availableLanguages: List<Language>,
    showLanguageSetting: Boolean,
    onEvent: (CardInfoStore.Event) -> Unit,
) {
    var showLanguageDialog by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(AppTheme.colors.background.secondaryTwo)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (showLanguageSetting) {
                TableCardInfoItem(
                    title = stringResource(R.string.language_title),
                    description = stringResource(selectedLanguage.nameResId()),
                    onClick = {
                        showLanguageDialog = true
                    }
                )
                CastDivider()
            }

            if (BuildConfig.DEBUG) {
                TableCardInfoItem(
                    title = stringResource(R.string.create_test_for_build_debug),
                    description = stringResource(R.string.create_test_for_build_debug),
                    onClick = {
                        onEvent(
                            CardInfoStore.Event.CreateTestForBuildDebug
                        )
                    }
                )
                CastDivider()
            }

            TableCardInfoSwitchItem(
                title = stringResource(R.string.notification_toggle_title),
                description = stringResource(R.string.notification_toggle_description),
                checked = notificationsEnabled,
                settingsWithNotificationOpen = settingsWithNotificationOpen,
                onEvent = onEvent
            )
        }
    }

    if (showLanguageDialog) {
        LanguageSelectionDialog(
            selectedLanguage = selectedLanguage,
            availableLanguages = availableLanguages,
            onLanguageSelected = {
                onEvent(CardInfoStore.Event.SelectLanguage(it))
                showLanguageDialog = false
            },
            onDismiss = { showLanguageDialog = false }
        )
    }
}

@Composable
private fun LanguageSelectionDialog(
    selectedLanguage: Language,
    availableLanguages: List<Language>,
    onLanguageSelected: (Language) -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(AppTheme.colors.background.secondaryTwo)
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.language_title),
                    style = AppTheme.textStyle.subtitleTwo,
                    color = AppTheme.colors.text.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                availableLanguages.forEach { language ->
                    LanguageItem(
                        language = language,
                        isSelected = language.code == selectedLanguage.code,
                        onClick = { onLanguageSelected(language) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LanguageItem(
    language: Language,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            colors = RadioButtonColors(
                selectedColor = AppTheme.colors.background.blue,
                unselectedColor = AppTheme.colors.background.primary,
                disabledSelectedColor = AppTheme.colors.background.blueLight,
                disabledUnselectedColor = AppTheme.colors.background.mask
            ),
            onClick = onClick
        )
        Text(
            text = stringResource(language.nameResId()),
            style = AppTheme.textStyle.bodyTwo,
            color = AppTheme.colors.text.primary,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun CastDivider() {
    HorizontalDivider(
        modifier = Modifier
            .height(1.dp),
        color = AppTheme.colors.background.mask
    )
}
