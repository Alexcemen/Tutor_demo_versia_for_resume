package ru.project.tutor.ui.screen.test_manager.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.theme.AppTheme


@Composable
@Preview(showBackground = true)
internal fun RoundButtonPreview() {
    RoundButton(
        imageId = R.drawable.ic_setting
    ) {}
}

@Composable
fun RoundButton(
    imageId: Int,
    modifier: Modifier = Modifier,
    size: Dp = 38.dp,
    isEnabled: Boolean = true,
    contentColor: Color = AppTheme.colors.background.primary,
    imageColor: Color = AppTheme.colors.background.basic,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = AppTheme.colors.text.primary,
            containerColor = contentColor,
            disabledContentColor = contentColor,
            disabledContainerColor = AppTheme.colors.text.placeholder
        ),
        enabled = isEnabled,
        modifier = modifier.size(size),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            painter = painterResource(imageId),
            contentDescription = "",
            colorFilter = ColorFilter.tint(imageColor),
            modifier = Modifier.fillMaxSize(0.5f)
        )
    }
}