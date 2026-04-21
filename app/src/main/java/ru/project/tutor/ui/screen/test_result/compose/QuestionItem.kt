package ru.project.tutor.ui.screen.test_result.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.domain.models.question.QuestionData
import ru.project.tutor.ui.models.AnswerChoiceDataUi
import ru.project.tutor.ui.models.QuestionWorkoutUi


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QuestionItemTestResultPreview() {
    AppTheme(true) {
        QuestionItemTestResult(
            question = QuestionWorkoutUi(
                QuestionData(
                    id = 1,
                    questionText = "В каком году создали Android?",
                    testId = 1,
                    position = 1,
                    isMultipleAnswerChoice = false
                ),
                listOf(
                    AnswerChoiceDataUi(
                        AnswerChoiceData(
                            id = 1,
                            text = "Его вообще не создали о чем вы?",
                            isRightAnswer = false,
                            questionId = 1
                        ),
                        isSelectedAnswer = true
                    ),
                    AnswerChoiceDataUi(
                        AnswerChoiceData(
                            id = 2,
                            text = "Его вообще не создали о чем вы? Большой ответ очень большой и еще больше разве может меньше ответ быть?",
                            isRightAnswer = true,
                            questionId = 1
                        )
                    ),
                    AnswerChoiceDataUi(
                        AnswerChoiceData(
                            id = 3,
                            text = "2008",
                            isRightAnswer = false,
                            questionId = 1
                        )
                    )
                ),
                questionStatus = QuestionStatusTestResult.ERROR,
            )
        )
    }
}


@Composable
fun QuestionItemTestResult(
    question: QuestionWorkoutUi,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(AppTheme.colors.background.secondaryTwo)
            .padding(16.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = if (question.questionStatus == QuestionStatusTestResult.CORRECT) AppTheme.colors.background.green
                        else AppTheme.colors.background.red
                    )
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(
                        if (question.questionStatus == QuestionStatusTestResult.CORRECT) R.string.right
                        else R.string.error
                    ),
                    style = AppTheme.textStyle.captionOne,
                    color = AppTheme.colors.text.whiteUniform
                )
            }
            SpacerHeight(8.dp)
            Text(
                text = question.question.questionText,
                style = AppTheme.textStyle.subheadOne,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(start = 4.dp)
            )
            question.answers.forEach { answer ->
                SpacerHeight(8.dp)
                AnswerItemTestResult(
                    answer = answer,
                    questionStatus = question.questionStatus,
                    isMultipleAnswerChoice = question.question.isMultipleAnswerChoice
                )
            }
        }
    }
}