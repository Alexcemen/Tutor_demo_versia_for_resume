package ru.project.tutor.ui.screen.test_info.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable

@Preview
@Composable
private fun TelegramBannerCardPreview() {
    AppTheme {
        TelegramBannerCard(
            onClose = {},
            onClick = {},
        )
    }
}

@Composable
fun TelegramBannerCard(
    onClose: () -> Unit,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(AppTheme.colors.background.secondaryTwo)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.telegram_banner_title),
                    style = AppTheme.textStyle.subtitleOne,
                    color = AppTheme.colors.text.blue,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .size(12.dp)
                        .noRippleClickable { onClose() },
                    colorFilter = ColorFilter.tint(color = AppTheme.colors.background.secondary)
                )
            }

            Text(
                text = stringResource(R.string.telegram_banner_description),
                style = AppTheme.textStyle.captionTwo,
                color = AppTheme.colors.text.placeholder,
                modifier = Modifier.padding(top = 8.dp, end = 16.dp)
            )

            Text(
                text = stringResource(R.string.telegram_banner_button),
                style = AppTheme.textStyle.subheadOne,
                color = AppTheme.colors.background.primary,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .noRippleClickable(onClick = onClick)
            )
        }
    }
}
