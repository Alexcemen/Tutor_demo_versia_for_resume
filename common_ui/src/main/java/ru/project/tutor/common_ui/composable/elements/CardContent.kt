package ru.project.tutor.common_ui.composable.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.composable.theme.AppTheme

@Preview(showBackground = true)
@Composable
fun CardContentPreview() {
    CardContent() {
        Text(text = "Content")
    }
}

@Composable
fun CardContent(
    modifier: Modifier = Modifier,
    paddingContentTop: Dp = 24.dp,
    paddingContentBottom: Dp = 0.dp,
    paddingHorizontal: Dp = 16.dp,
    paddingCardTop: Dp = 16.dp,
    roundedShape: Shape = RoundedCornerShape(
        CardContentConst.ROUNDED_DEFAULT,
        CardContentConst.ROUNDED_DEFAULT,
        0.dp,
        0.dp
    ),
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .padding(top = paddingCardTop)
            .clip(shape = roundedShape)
            .background(color = AppTheme.colors.background.basic),
        contentAlignment = contentAlignment,
        content = {
            Box(
                modifier = Modifier
                    .padding(
                        horizontal = paddingHorizontal
                    )
                    .padding(top = paddingContentTop, bottom = paddingContentBottom),
                content = content
            )
        }
    )
}

object CardContentConst {
    val ROUNDED_DEFAULT = 20.dp
    val PADDING_BETWEEN_DEFAULT = 12.dp

    val defaultModifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
}