package ru.project.tutor.common_ui.composable.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
internal fun SecondaryButtonPreview() {
    SecondaryButton(text = "Ok") {}
}

@Composable
fun SecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    minHeight: Dp = 56.dp,
    width: Dp? = null,
    isEnabled: Boolean = true,
    borderColor: Color = AppTheme.colors.background.primary,
    textColor: Color = AppTheme.colors.text.primary,
    onClick: () -> Unit,
) {
    val modifier = if (width == null) {
        modifier
            .defaultMinSize(minHeight = minHeight)
            .fillMaxWidth()
    } else {
        modifier
            .defaultMinSize(minHeight = minHeight)
            .width(width)
    }
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, borderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = textColor,
            disabledContentColor = AppTheme.colors.text.placeholder,
        ),
        enabled = isEnabled,
        modifier = modifier
    ) {
        Text(
            text,
            color = textColor,
            letterSpacing = 0.sp,
            style = AppTheme.textStyle.bodyOne,
        )
    }
}
