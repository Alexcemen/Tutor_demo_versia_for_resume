package ru.project.tutor.ui.screen.test_process.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.SpacerWidth
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.ui.models.AnswerChoiceDataUi
import ru.project.tutor.ui.screen.test_process.TestProcessStore


@Composable
fun AnswerItem(
    answer: AnswerChoiceDataUi,
    questionStatus: QuestionStatus,
    showRightAnswer: Boolean,
    isMultipleAnswerChoice: Boolean,
    doNotMarkMultipleAnswerChoice: Boolean,
    onEvent: (TestProcessStore.Event) -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = questionStatus.colorAnswerBorder(
                    showRightAnswer = showRightAnswer,
                    isSelectedAnswer = answer.isSelectedAnswer,
                    isRightAnswer = answer.answerChoiceData.isRightAnswer
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .background(AppTheme.colors.background.secondaryTwo)
            .padding(16.dp)
            .fillMaxWidth()
            .noRippleClickable {
                onEvent(
                    TestProcessStore.Event.ClickOnAnswerChoice(
                        selectedAnswer = answer
                    )
                )

            }
    ) {
        Image(
            painterResource(
                if (isMultipleAnswerChoice) R.drawable.ic_square_checkmark_correct
                else
                    if (doNotMarkMultipleAnswerChoice) R.drawable.ic_square_checkmark_correct
                    else R.drawable.ic_correct_choice
            ),
            "",
            colorFilter = ColorFilter.tint(
                color = questionStatus.colorAnswerBackground(
                    showRightAnswer = showRightAnswer,
                    isSelectedAnswer = answer.isSelectedAnswer,
                )
            ),
            modifier = Modifier
                .size(24.dp)
        )
        SpacerWidth(12.dp)
        Text(
            text = answer.answerChoiceData.text,
            style = AppTheme.textStyle.subheadTwo,
            color = AppTheme.colors.text.primary
        )
    }
}

@Preview
@Composable
private fun AnswerItemPreviewWorkout() {
    AnswerItem(
        answer = AnswerChoiceDataUi(
            answerChoiceData = AnswerChoiceData(
                id = 2,
                text = "Его вообще не создали о чем вы? Большой ответ очень большой и еще больше разве может меньше ответ быть?",
                isRightAnswer = true,
                questionId = 1
            ),
            isSelectedAnswer = true
        ),
        questionStatus = QuestionStatus.CORRECT,
        showRightAnswer = true,
        isMultipleAnswerChoice = false,
        doNotMarkMultipleAnswerChoice = false,
        onEvent = {}
    )
}