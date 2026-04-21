package ru.project.tutor.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable

@Composable
fun CustomDragHandle() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .noRippleClickable {},
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .background(
                    color = AppTheme.colors.background.mask,
                    shape = RoundedCornerShape(1.4.dp)
                )
                .width(38.dp)
                .height(4.dp)
        )
        Spacer(Modifier.height(16.dp))
    }
}