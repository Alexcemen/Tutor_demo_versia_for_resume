package ru.project.tutor.ui.screen.test_process.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.SpacerWidth
import ru.project.tutor.common_ui.composable.theme.AppTheme
import java.util.Locale

@Preview
@Composable
private fun TimerPreview() {
    Timer(
        durationTesting = 30,
    )
}

@Composable
fun Timer(
    durationTesting: Int,
    onFinished: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_timer),
            contentDescription = "",
            colorFilter = ColorFilter.tint(color = AppTheme.colors.background.secondary),
            modifier = Modifier.size(21.dp)
        )
        SpacerWidth(8.dp)
        CountdownTimerText(
            durationTesting = durationTesting,
            onFinished = onFinished
        )
    }
}


@Composable
private fun CountdownTimerText(
    durationTesting: Int,
    onFinished: () -> Unit = {},
) {
    var remainingSecond by androidx.compose.runtime.saveable.rememberSaveable {
        mutableIntStateOf(durationTesting * 60)
    }

    LaunchedEffect(durationTesting) {
        remainingSecond = durationTesting * 60
        while (remainingSecond > 0) {
            delay(1000L)
            remainingSecond--
        }
        if (remainingSecond == 0) onFinished()
    }

    val minutes = remainingSecond / 60
    val seconds = remainingSecond % 60

    Text(
        text = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds),
        style = AppTheme.textStyle.subtitleOne,
        color = AppTheme.colors.text.placeholder
    )
}