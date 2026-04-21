package ru.project.tutor.ui.screen.card_info.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable


@Preview
@Composable
private fun TableCardInfoItemPreview() {
    TableCardInfoItem(
        title = "Политика конфиденциальности",
        description = "Прочитать описнаие обработку персональных данных",
        {}
    )
}

@Composable
fun TableCardInfoItem(
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.secondaryTwo)
            .padding(vertical = 16.dp)
            .noRippleClickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = AppTheme.textStyle.subheadOne,
            color = AppTheme.colors.text.blue
        )
        Text(
            text = description,
            style = AppTheme.textStyle.captionTwo,
            color = AppTheme.colors.text.placeholder
        )
    }
}