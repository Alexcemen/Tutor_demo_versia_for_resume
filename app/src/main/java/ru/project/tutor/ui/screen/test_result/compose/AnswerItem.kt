package ru.project.tutor.ui.screen.test_result.compose

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.composable.elements.SpacerWidth
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.ui.models.AnswerChoiceDataUi

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AnswerItemTestResultPreview() {
    AppTheme(true) {
        AnswerItemTestResult(
            answer = AnswerChoiceDataUi(
                AnswerChoiceData(
                    id = 1,
                    text = "Его вообще не создали о чем вы?",
                    isRightAnswer = true,
                    questionId = 1
                ),
                isSelectedAnswer = true
            ),
            questionStatus = QuestionStatusTestResult.CORRECT,
            isMultipleAnswerChoice = false
        )
    }
}


@Composable
fun AnswerItemTestResult(
    answer: AnswerChoiceDataUi,
    questionStatus: QuestionStatusTestResult,
    isMultipleAnswerChoice: Boolean,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = questionStatus.colorAnswerBorder(
                    isSelectedAnswer = answer.isSelectedAnswer,
                    isRightAnswer = answer.answerChoiceData.isRightAnswer
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(start = 12.dp, bottom = 8.dp, top = 8.dp, end = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(
                id = questionStatus.painterIcon(
                    isRightAnswer = answer.answerChoiceData.isRightAnswer,
                    isSelectedIcon = answer.isSelectedAnswer,
                    isMultipleAnswerChoice = isMultipleAnswerChoice
                )
            ),
            modifier = Modifier.size(16.dp),
            contentDescription = "back",
            colorFilter = ColorFilter.tint(
                color = questionStatus.colorAnswerBackground(
                    isSelectedAnswer = answer.isSelectedAnswer,
                    isRightAnswer = answer.answerChoiceData.isRightAnswer
                )
            ),
        )
        SpacerWidth(8.dp)
        Text(
            text = answer.answerChoiceData.text,
            style = AppTheme.textStyle.captionTwo,
            color = AppTheme.colors.text.primary,
        )
    }
}