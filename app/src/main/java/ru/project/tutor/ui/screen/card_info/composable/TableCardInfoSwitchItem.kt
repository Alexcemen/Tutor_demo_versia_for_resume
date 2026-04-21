package ru.project.tutor.ui.screen.card_info.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.ui.screen.card_info.CardInfoStore

@Preview
@Composable
private fun TableCardInfoSwitchItemPreview() {
    AppTheme {
        TableCardInfoSwitchItem(
            title = "Уведомления",
            description = "Напоминания",
            checked = true,
            settingsWithNotificationOpen = true,
            onEvent = {},
        )
    }
}

@Composable
fun TableCardInfoSwitchItem(
    title: String,
    description: String,
    checked: Boolean,
    settingsWithNotificationOpen: Boolean,
    onEvent: (CardInfoStore.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.secondaryTwo)
            .padding(
                top = 16.dp,
                bottom = if (!settingsWithNotificationOpen) 16.dp
                else 0.dp
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {
                    onEvent(CardInfoStore.Event.ToggleSettingsWithNotification)
                }
        ) {
            Text(
                text = title,
                style = AppTheme.textStyle.subheadOne,
                color = AppTheme.colors.text.blue,
            )
        }
        if (settingsWithNotificationOpen) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = description,
                    style = AppTheme.textStyle.captionTwo,
                    color = AppTheme.colors.text.placeholder,
                )
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        onEvent(CardInfoStore.Event.ToggleNotifications(it))
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = AppTheme.colors.background.green,
                    )
                )
            }
        }
    }
}
