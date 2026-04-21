package ru.project.tutor.ui.screen.questions_list.compose

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.elements.SpacerWidth
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.ui.models.AnswerChoiceUi
import ru.project.tutor.ui.models.QuestionInfoCardUi



@Composable
fun QuestionInfoCard(
    state: QuestionInfoCardUi,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 0f else 180f,
        label = "arrowRotation"
    )

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(AppTheme.colors.background.secondaryTwo)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_question_mark),
                contentDescription = "",
                colorFilter = ColorFilter.tint(AppTheme.colors.text.placeholder),
                modifier = Modifier.size(14.dp)
            )
            SpacerWidth(8.dp)
            Text(
                text = stringResource(R.string.question_number, state.position),
                style = AppTheme.textStyle.captionOne,
                color = AppTheme.colors.text.placeholder,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 12.dp, end = 8.dp)
                .fillMaxWidth()
                .noRippleClickable { isExpanded = !isExpanded },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = state.questionText,
                style = AppTheme.textStyle.bodyTwo,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.weight(1f)
            )
            SpacerWidth(12.dp)
            Image(
                painter = painterResource(R.drawable.ic_arrow),
                contentDescription = "",
                colorFilter = ColorFilter.tint(AppTheme.colors.text.placeholder),
                modifier = Modifier
                    .size(18.dp)
                    .rotate(rotation)
            )
        }

        if (isExpanded) {
            state.answerChoices.forEachIndexed { index, answer ->
                AnswerChoice(answer)
                if (index != state.answerChoices.lastIndex) SpacerHeight(8.dp)
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
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QuestionInfoCardPreview() {
    AppTheme(true) {
        QuestionInfoCard(
            state = QuestionInfoCardUi(
                questionId = 1,
                position = 1,
                questionNumber = 1,
                questionText = "Что такое математика?",
                answerChoices = listOf(
                    AnswerChoiceUi(
                        id = 1,
                        text = "Наука",
                        isRightAnswer = false
                    ),
                    AnswerChoiceUi(
                        id = 1,
                        text = "Наука",
                        isRightAnswer = true
                    ),
                    AnswerChoiceUi(
                        id = 1,
                        text = "Наука",
                        isRightAnswer = false
                    ),
                    AnswerChoiceUi(
                        id = 1,
                        text = "Науак",
                        isRightAnswer = false
                    )
                ),
            ),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QuestionInfoCardPreview1() {
    AppTheme(true) {
        QuestionInfoCard(
            state = QuestionInfoCardUi(
                questionId = 1,
                position = 1,
                questionNumber = 1,
                questionText = "Что такое математика dasfgsdafgaergeasdrg eargher erhgeqrg ewrgheqrg eqrgherg edrgheqr weregergeq?",
                answerChoices = listOf(
                    AnswerChoiceUi(
                        id = 1,
                        text = "Наука",
                        isRightAnswer = false
                    ),
                    AnswerChoiceUi(
                        id = 1,
                        text = "Наука",
                        isRightAnswer = true
                    ),
                    AnswerChoiceUi(
                        id = 1,
                        text = "Наука",
                        isRightAnswer = false
                    ),
                    AnswerChoiceUi(
                        id = 1,
                        text = "Науак",
                        isRightAnswer = false
                    )
                ),
            ),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QuestionInfoCardPreview2() {
    AppTheme(true) {
        QuestionInfoCard(
            state = QuestionInfoCardUi(
                questionId = 1,
                position = 1,
                questionNumber = 1,
                questionText = "Что такое математика?",
                answerChoices = listOf(
                    AnswerChoiceUi(
                        id = 1,
                        text = "Наука",
                        isRightAnswer = false
                    ),
                    AnswerChoiceUi(
                        id = 1,
                        text = "Наука",
                        isRightAnswer = true
                    ),
                    AnswerChoiceUi(
                        id = 1,
                        text = "Наука",
                        isRightAnswer = false
                    ),
                    AnswerChoiceUi(
                        id = 1,
                        text = "Науак",
                        isRightAnswer = false
                    )
                ),
            ),
        )
    }
}