package ru.project.tutor.ui.screen.test_manager.composable

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.project.tutor.common_ui.composable.theme.AppTheme


@Composable
@Preview(showBackground = true)
internal fun ButtonWithTextPreview() {
    ButtonWithText(
        text = "dfgsd"
    ) {}
}

@Composable
fun ButtonWithText(
    text: String,
    modifier: Modifier = Modifier,
    height: Dp = 38.dp,
    width: Dp? = null,
    isEnabled: Boolean = true,
    contentColor: Color = AppTheme.colors.background.primary,
    textColor: Color = AppTheme.colors.text.reverse,
    onClick: () -> Unit,
) {
    var clickEnabled by remember(isEnabled) { mutableStateOf(true) }
    LaunchedEffect(key1 = clickEnabled) {
        if (!clickEnabled) {
            delay(1000)
            clickEnabled = true
        }
    }
    val modifier = if (width == null) {
        modifier.height(height)
    } else {
        modifier
            .height(height)
            .width(width)
    }
    TextButton(
        onClick = {
            if (clickEnabled && isEnabled) {
                clickEnabled = false
                onClick()
            }
        },
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = AppTheme.colors.text.primary,
            containerColor = contentColor,
            disabledContentColor = contentColor,
            disabledContainerColor = AppTheme.colors.text.placeholder
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