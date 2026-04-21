package ru.project.tutor.ui.screen.starting_testing.composable

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.elements.TextField
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.models.options_for_testing.OptionsStartTestingUi
import ru.project.tutor.ui.screen.starting_testing.StartingTestingStore


@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OptionsForWorkoutPreview() {
    AppTheme(true) {
        OptionsForWorkout(
            options = OptionsStartTestingUi(
                isTimerEnabled = true,
                durationTesting = 0,
                shuffleQuestions = true,
                shuffleAnswers = true,
                showRightAnswer = false,
                doNotMarkMultipleAnswerChoice = true,
                isRandomQuestionsEnable = true,
                countRandomQuestions = 0
            ),
            countQuestions = 12,
            onEvent = {}
        )
    }
}


@Composable
fun OptionsForWorkout(
    options: OptionsStartTestingUi,
    countQuestions: Int,
    onEvent: (StartingTestingStore.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(AppTheme.colors.background.secondaryTwo)
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                SetTimerOption(
                    isTimerEnabled = options.isTimerEnabled,
                    durationTesting = options.durationTesting,
                    onEvent = onEvent
                )

                CastDivider()

                ShuffleQuestionsOption(
                    shuffleQuestions = options.shuffleQuestions,
                    onEvent = onEvent
                )

                CastDivider()

                ShuffleAnswersOption(
                    shuffleAnswers = options.shuffleAnswers,
                    onEvent = onEvent
                )

                CastDivider()

                TagMultipleAnswerChoiceOption(
                    doNotMarkMultipleAnswerChoice = options.doNotMarkMultipleAnswerChoice,
                    onEvent = onEvent
                )

                CastDivider()

                RandomQuestionsOption(
                    isRandomQuestionsEnable = options.isRandomQuestionsEnable,
                    countQuestions = countQuestions,
                    countRandomQuestions = options.countRandomQuestions,
                    onEvent = onEvent
                )

                CastDivider()

                ShowRightAnswerOption(
                    showRightAnswer = options.showRightAnswer,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Composable
private fun SetTimerOption(
    isTimerEnabled: Boolean,
    durationTesting: Int,
    onEvent: (StartingTestingStore.Event) -> Unit,
) {
    QuestionSettingOption(
        text = stringResource(R.string.set_timer),
        isSelected = isTimerEnabled,
        onClick = {
            onEvent(
                StartingTestingStore.Event.ToggleDurationOption
            )
        }
    )

    if (isTimerEnabled) {
        TextField(
            text = if (durationTesting == -1) ""
            else durationTesting.toString(),
            label = stringResource(R.string.start_testing_tittle_two),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            onValueChange = { input ->
                val digitsOnly = input.filter { it.isDigit() }
                val intValue = digitsOnly.toIntOrNull() ?: 0
                onEvent(
                    StartingTestingStore.Event.UpdateDuration(
                        duration = intValue
                    )
                )
            }
        )
        SpacerHeight(16.dp)
    }
}

@Composable
private fun ShuffleQuestionsOption(
    shuffleQuestions: Boolean,
    onEvent: (StartingTestingStore.Event) -> Unit,
) {
    QuestionSettingOption(
        text = stringResource(R.string.shuffle_questions),
        isSelected = shuffleQuestions,
        onClick = {
            onEvent(
                StartingTestingStore.Event.ToggleShuffleQuestions
            )
        }
    )
}

@Composable
private fun ShuffleAnswersOption(
    shuffleAnswers: Boolean,
    onEvent: (StartingTestingStore.Event) -> Unit,
) {
    QuestionSettingOption(
        text = stringResource(R.string.shuffle_answers),
        isSelected = shuffleAnswers,
        onClick = {
            onEvent(
                StartingTestingStore.Event.ToggleShuffleAnswers
            )
        }
    )
}

@Composable
private fun TagMultipleAnswerChoiceOption(
    doNotMarkMultipleAnswerChoice: Boolean,
    onEvent: (StartingTestingStore.Event) -> Unit,
) {
    QuestionSettingOption(
        text = stringResource(R.string.tag_multiple_answer_choice),
        isSelected = doNotMarkMultipleAnswerChoice,
        onClick = {
            onEvent(
                StartingTestingStore.Event.ToggleTagMultipleAnswerChoice
            )
        }
    )
}

@Composable
private fun ShowRightAnswerOption(
    showRightAnswer: Boolean,
    onEvent: (StartingTestingStore.Event) -> Unit,
) {
    QuestionSettingOption(
        text = stringResource(R.string.show_right_answer),
        isSelected = showRightAnswer,
        onClick = {
            onEvent(
                StartingTestingStore.Event.ToggleShowRightAnswer
            )
        }
    )
}

@Composable
fun RandomQuestionsOption(
    isRandomQuestionsEnable: Boolean,
    countQuestions: Int,
    countRandomQuestions: Int,
    onEvent: (StartingTestingStore.Event) -> Unit,
) {
    QuestionSettingOption(
        text = stringResource(R.string.set_count_random_questions),
        isSelected = isRandomQuestionsEnable,
        onClick = {
            onEvent(
                StartingTestingStore.Event.ToggleRandomQuestionsOption
            )
        }
    )

    if (isRandomQuestionsEnable) {
        val countQuestionsString =
            pluralStringResource(
                R.plurals.count_questions,
                countQuestions,
                countQuestions
            )
        Text(
            text = stringResource(
                R.string.start_testing_hint_input_field_for_count_random_questions,
                countQuestionsString
            ),
            color = AppTheme.colors.text.primary,
            style = AppTheme.textStyle.captionTwo,
            modifier = Modifier.padding(end = 40.dp)
        )
        SpacerHeight(16.dp)
        TextField(
            text = if (countRandomQuestions == -1) ""
            else countRandomQuestions.toString(),
            label = stringResource(R.string.set_count_random_questions_hint),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            onValueChange = { input ->
                val digitsOnly = input.filter { it.isDigit() }
                val intValue = digitsOnly.toIntOrNull() ?: 0
                onEvent(
                    StartingTestingStore.Event.UpdateCountRandomQuestions(
                        countRandomQuestions = intValue
                    )
                )
            },
        )
        SpacerHeight(16.dp)
    }
}

@Composable
private fun CastDivider() {
    HorizontalDivider(
        modifier = Modifier
            .height(1.dp),
        color = AppTheme.colors.background.mask
    )
}