package ru.project.tutor.ui.screen.starting_testing.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.ui.screen.starting_testing.StartingTestingStore


@Composable
@Preview(showBackground = false)
fun TrainingExamSwitcherPreview() {
    TrainingExamSwitcher(
        isExam = true,
        onEvent = {}
    )
}

@Composable
fun TrainingExamSwitcher(
    isExam: Boolean,
    onEvent: (StartingTestingStore.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedBg = AppTheme.colors.background.primary
    val selectedTxt = AppTheme.colors.text.reverse
    val unselectedBg = AppTheme.colors.background.secondaryTwo
    val unselectedTxt = AppTheme.colors.text.primary

    Row(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(unselectedBg)
    ) {
        Segment(
            text = stringResource(R.string.starting_testing_switcher_work),
            selected = !isExam,
            selectedBg = selectedBg,
            selectedTxt = selectedTxt,
            unselectedBg = unselectedBg,
            unselectedTxt = unselectedTxt,
            onClick = {
                onEvent(
                    StartingTestingStore.Event.ToggleExamMode(false)
                )
            },
            modifier = Modifier.weight(1f)
        )
        Segment(
            text = stringResource(R.string.starting_testing_switcher_exam),
            selected = isExam,
            selectedBg = selectedBg,
            selectedTxt = selectedTxt,
            unselectedBg = unselectedBg,
            unselectedTxt = unselectedTxt,
            onClick = {
                onEvent(
                    StartingTestingStore.Event.ToggleExamMode(true)
                )
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun Segment(
    text: String,
    selected: Boolean,
    selectedBg: Color,
    selectedTxt: Color,
    unselectedBg: Color,
    unselectedTxt: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = if (selected) selectedBg else unselectedBg,
            )
            .noRippleClickable {
                onClick()
            }
    ) {
        Text(
            text = text,
            style = AppTheme.textStyle.captionTwo,
            color = if (selected) selectedTxt else unselectedTxt
        )
    }
}