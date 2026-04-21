package ru.project.tutor.ui.screen.card_info.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.BuildConfig
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.utils.showError

@Preview
@Composable
private fun AppVersionInfoPreview() {
    AppVersionInfo()
}

@Composable
fun AppVersionInfo() {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(109.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(AppTheme.colors.background.secondaryTwo)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(if (isSystemInDarkTheme()) R.drawable.ic_app_icon_dark else R.drawable.ic_app_icon),
            "",
            modifier = Modifier
                .size(77.dp)
                .combinedClickable(onLongClick = {
                    context.showError("Flavour = ${BuildConfig.flavourName}, isDebug = ${BuildConfig.DEBUG}")
                }) {}
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = AppTheme.textStyle.largeTitleOne,
                color = AppTheme.colors.text.primary,
            )
            Text(
                text = "Версия ${BuildConfig.VERSION_NAME}",
                style = AppTheme.textStyle.subheadThree,
                color = AppTheme.colors.text.primary,
            )
        }
    }
}