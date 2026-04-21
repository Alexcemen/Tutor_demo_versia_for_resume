package ru.project.tutor.ui.screen.test_process.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.SpacerWidth
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.ui.screen.test_process.TestProcessStore


@Composable
fun MainToolbarTestProcess(
    text: String,
    durationTesting: Int,
    isFavorite: Boolean,
    showFavorite: Boolean,
    backgroundColor: Color = AppTheme.colors.background.basic,
    onEvent: (TestProcessStore.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        Spacer(
            modifier = Modifier.height(
                WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
            )
        )
        ContentToolBar(
            text = text,
            durationTesting = durationTesting,
            isFavorite = isFavorite,
            showFavorite = showFavorite,
            onEvent = onEvent
        )
    }
}

@Composable
private fun ContentToolBar(
    text: String,
    durationTesting: Int,
    isFavorite: Boolean,
    showFavorite: Boolean,
    onEvent: (TestProcessStore.Event) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = "back",
            colorFilter = ColorFilter.tint(color = AppTheme.colors.background.primary),
            modifier = Modifier
                .noRippleClickable {
                    onEvent(TestProcessStore.Event.ConfirmExitBack)
                }
                .size(14.dp)
        )
        Text(
            text = text,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            style = AppTheme.textStyle.subtitleOne,
            color = AppTheme.colors.text.primary
        )
        if (durationTesting >= 0) {
            Timer(
                durationTesting = durationTesting,
                onFinished = {
                    onEvent(
                        TestProcessStore.Event.TimerFinished
                    )
                }
            )
            SpacerWidth(16.dp)
        }
        if (showFavorite) {
            Image(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "",
                colorFilter = ColorFilter.tint(
                    color = if (isFavorite) {
                        AppTheme.colors.background.yellow
                    } else {
                        AppTheme.colors.background.secondary
                    }
                ),
                modifier = Modifier
                    .noRippleClickable {
                        onEvent(
                            TestProcessStore.Event.ToggleFavorite
                        )
                    }
                    .size(24.dp)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun ContentToolBarWorkoutPreview() {
    MainToolbarTestProcess(
        text = "Вопрос 1",
        durationTesting = 30,
        isFavorite = true,
        showFavorite = false,
        onEvent = {}
    )
}
