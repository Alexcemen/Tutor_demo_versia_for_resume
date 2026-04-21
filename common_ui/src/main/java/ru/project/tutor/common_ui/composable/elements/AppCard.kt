package ru.project.tutor.common_ui.composable.elements

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.composable.theme.AppTheme

@Composable
fun AppCard(
    modifier: Modifier,
    containerColor: Color = AppTheme.colors.background.basic,
    onClick: (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(16.dp),
    Content: @Composable ColumnScope.() -> Unit,
) {
    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor),
            shape = shape,
        ) {
            Content()
        }
        return
    }
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor),
        shape = shape,
    ) {
        Content()
    }
}
