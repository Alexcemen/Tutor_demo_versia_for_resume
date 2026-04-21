package ru.project.tutor.ui.screen.test_manager.composable.question_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.elements.SpacerWidth
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.ui.screen.test_manager.TestManagerStore
import ru.project.tutor.ui.screen.test_manager.models.AnswerChoiceHighlightTextUi
import ru.project.tutor.ui.screen.test_manager.models.QuestionInfoCardHighlightTextUi

@Composable
@Preview(showBackground = true)
private fun QuestionInfoCardPreview() {
    QuestionInfoCard(
        state = QuestionInfoCardHighlightTextUi(
            questionId = 1,
            position = 1,
            questionNumber = 1,
            questionText = AnnotatedString("Что такое математика?"),
            answerChoices = listOf(
                AnswerChoiceHighlightTextUi(
                    id = 1,
                    highlightText = AnnotatedString("Наука"),
                    isRightAnswer = false
                ),
                AnswerChoiceHighlightTextUi(
                    id = 1,
                    highlightText = AnnotatedString("Наука"),
                    isRightAnswer = true
                ),
                AnswerChoiceHighlightTextUi(
                    id = 1,
                    highlightText = AnnotatedString("Наука"),
                    isRightAnswer = false
                ),
                AnswerChoiceHighlightTextUi(
                    id = 1,
                    highlightText = AnnotatedString("Наука"),
                    isRightAnswer = false
                )
            ),
        ),
        onClick = {}
    )
}


@Composable
fun QuestionInfoCard(
    state: QuestionInfoCardHighlightTextUi,
    onClick: (TestManagerStore.Event) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.ic_question_mark),
                contentDescription = "",
                colorFilter = ColorFilter.tint(AppTheme.colors.text.placeholder),
                modifier = Modifier.size(14.dp)
            )
            SpacerWidth(8.dp)
            Text(
                text = stringResource(R.string.question_number, state.questionNumber),
                style = AppTheme.textStyle.captionOne,
                color = AppTheme.colors.text.placeholder,
                modifier = Modifier.weight(1f)
            )

            Image(
                painter = painterResource(R.drawable.ic_three_stripes),
                contentDescription = "",
                colorFilter = ColorFilter.tint(AppTheme.colors.background.mask),
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(16.dp)
                    .noRippleClickable {
                        onClick(
                            TestManagerStore.Event.OpenQuestionActionsBottomSheet(
                                questionId = state.questionId
                            )
                        )
                    }
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 12.dp, end = 24.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = state.questionText,
                style = AppTheme.textStyle.bodyTwo,
                color = AppTheme.colors.text.primary
            )
        }
        state.answerChoices.forEach { answer ->
            SpacerHeight(8.dp)
            AnswerChoice(answer)
        }
        SpacerHeight(12.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_dotted_line),
                contentDescription = "",
                colorFilter = ColorFilter.tint(AppTheme.colors.background.mask),
                modifier = Modifier
                    .height(1.dp)
                    .width(96.dp)
            )
        }
    }
}