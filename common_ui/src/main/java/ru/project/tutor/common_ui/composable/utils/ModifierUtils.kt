package ru.project.tutor.common_ui.composable.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.delay

fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit,
): Modifier = composed {
    this.then(
        Modifier.clickable(
            enabled = enabled,
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = onClick
        )
    )
}

fun Modifier.noDoubleClick(
    delay: Long = 1000,
    onClick: () -> Unit,
): Modifier = composed {
    var clickEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(clickEnabled) {
        if (!clickEnabled) {
            delay(delay)
            clickEnabled = true
        }
    }

    this.then(
        Modifier.clickable(
            enabled = clickEnabled,
            onClick = {
                if (clickEnabled) {
                    clickEnabled = false
                    onClick()
                }
            }
        )
    )
}