package ru.project.tutor.ui.screen.test_info.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.composable.elements.SpacerWidth
import ru.project.tutor.common_ui.composable.theme.AppTheme

@Composable
@Preview
private fun CircularPagerIndicatorPreview() {
    CircularPagerIndicator(1)
}

private const val VISIBLE_DOTS = 3

@Composable
fun CircularPagerIndicator(
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    val actualPage = currentPage % VISIBLE_DOTS
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(VISIBLE_DOTS) { index ->
            val isActive = index == actualPage

            if (isActive) {
                ActiveDot()
            } else {
                InactiveDot()
            }

            if (index != 2) {
                SpacerWidth(8.dp)
            }
        }
    }
}

@Composable
private fun InactiveDot() {
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = AppTheme.colors.background.mask,
                shape = CircleShape
            )
    )
}

@Composable
private fun ActiveDot() {
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(
                color = AppTheme.colors.background.primary,
            )
    )
}

@Composable
@Preview
private fun InactiveDotPreview() {
    InactiveDot()
}

@Composable
@Preview
private fun ActiveDotPreview() {
    ActiveDot()
}
