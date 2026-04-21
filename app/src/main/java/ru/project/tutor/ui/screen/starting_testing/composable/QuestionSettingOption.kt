package ru.project.tutor.ui.screen.starting_testing.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable

@Preview(showBackground = true)
@Composable
fun QuestionSettingOptionPreview() {
    QuestionSettingOption(
        text = "Показывать правильный ответ",
        isSelected = false,
        onClick = {}
    )
}

@Composable
fun QuestionSettingOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .background(AppTheme.colors.background.secondaryTwo)
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = AppTheme.textStyle.subheadThree,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.weight(1f)
            )
            Image(
                painterResource(R.drawable.ic_square_checkmark_correct),
                "",
                colorFilter = ColorFilter.tint(
                    if (isSelected) AppTheme.colors.background.primary
                    else AppTheme.colors.background.mask
                ),
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable {
                        onClick()
                    }
            )
        }
    }
}