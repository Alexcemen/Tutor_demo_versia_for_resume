package ru.project.tutor.common_ui.composable.elements.model

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.project.tutor.common_ui.composable.theme.AppTheme


@Composable
@Preview(showBackground = true)
internal fun ButtonWithBorderPreview() {
    ButtonWithBorder(text = "Ok") {}
}

@Composable
fun ButtonWithBorder(
    text: String,
    modifier: Modifier = Modifier,
    minHeight: Dp = 56.dp,
    isEnabled: Boolean = true,
    contentColor: Color = AppTheme.colors.background.basic,
    borderColor: Color = AppTheme.colors.background.primary,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor,
        ),
        colors = ButtonDefaults.buttonColors(
            contentColor = AppTheme.colors.text.primary,
            containerColor = contentColor,
            disabledContentColor = contentColor,
            disabledContainerColor = AppTheme.colors.text.placeholder
        ),
        enabled = isEnabled,
        modifier = modifier
            .defaultMinSize(minHeight = minHeight)
            .fillMaxWidth()
    ) {
        Text(
            text,
            color = AppTheme.colors.text.primary,
            letterSpacing = 0.sp,
            style = AppTheme.textStyle.bodyOne,
        )
    }
}