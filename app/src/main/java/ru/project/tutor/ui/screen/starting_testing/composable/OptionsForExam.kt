package ru.project.tutor.ui.screen.starting_testing.composable

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.elements.SpacerWidth
import ru.project.tutor.common_ui.composable.elements.TextField
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.models.options_for_testing.OptionsStartTestingUi
import ru.project.tutor.ui.screen.starting_testing.StartingTestingStore


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OptionsForExamPreview() {
    AppTheme(true) {
        OptionsForExam(
            options = OptionsStartTestingUi(
                durationTesting = 0,
                isRandomQuestionsEnable = true,
                countRandomQuestions = 0,
                shuffleAnswers = false,
                doNotMarkMultipleAnswerChoice = false,
                shuffleQuestions = true,
                isTimerEnabled = true,
                showRightAnswer = false
            ),
            countQuestions = 22,
            onEvent = {}
        )
    }
}


@Composable
fun OptionsForExam(
    options: OptionsStartTestingUi,
    countQuestions: Int,
    onEvent: (StartingTestingStore.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(AppTheme.colors.background.secondaryTwo)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(R.drawable.ic_lamp),
                "",
                colorFilter = ColorFilter.tint(AppTheme.colors.background.primary)
            )
            SpacerWidth(16.dp)
            Text(
                text = stringResource(R.string.starting_testing_options_for_exam_hint),
                color = AppTheme.colors.text.primary,
                style = AppTheme.textStyle.captionTwo
            )
        }
        SpacerHeight(16.dp)
        Text(
            text = stringResource(R.string.start_testing_tittle_two),
            style = AppTheme.textStyle.subheadTwo,
            color = AppTheme.colors.text.primary,
            modifier = Modifier
                .padding(vertical = 9.dp)
        )
        SpacerHeight(8.dp)
        TextField(
            text = if (options.durationTesting == -1) ""
            else options.durationTesting.toString(),
            label = stringResource(R.string.start_testing_hint_input_field),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { input ->
                val digitsOnly = input.filter { it.isDigit() }
                val intValue = digitsOnly.toIntOrNull() ?: 0
                onEvent(StartingTestingStore.Event.UpdateDuration(intValue))
            }
        )
        SpacerHeight(16.dp)
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(AppTheme.colors.background.secondaryTwo)
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            RandomQuestionsOption(
                isRandomQuestionsEnable = options.isRandomQuestionsEnable,
                countQuestions = countQuestions,
                countRandomQuestions = options.countRandomQuestions,
                onEvent = onEvent
            )
        }
    }
}