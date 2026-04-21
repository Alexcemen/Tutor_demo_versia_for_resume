package ru.project.tutor.ui.screen.test_process.composable

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.ui.screen.test_process.TestProcessStore

@Composable
fun IconNumberQuestion(
    numberQuestion: Int,
    value: QuestionStatus,
    showRightAnswer: Boolean,
    isSelectedIcon: Boolean,
    isClickable: Boolean,
    onEvent: (TestProcessStore.Event) -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .size(40.dp)
            .background(
                value.colorIconBackground(
                    isSelectedIcon = isSelectedIcon,
                    showRightAnswer = showRightAnswer
                )
            )
            .border(
                width = 1.dp,
                color = value.colorBorder(
                    isSelectedIcon = isSelectedIcon,
                    showRightAnswer = showRightAnswer
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable(
                enabled = isClickable,
                onClick = {
                    onEvent(
                        TestProcessStore.Event.OpenIconQuestion(
                            numberQuestion
                        )
                    )
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = numberQuestion.toString(),
            style = AppTheme.textStyle.subheadOne,
            color = value.colorIconText(
                isSelectedIcon = isSelectedIcon
            )
        )
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun IconNumberQuestionPreview() {
    Column {
        Row {
            IconNumberQuestion(
                numberQuestion = 1,
                value = QuestionStatus.ERROR,
                showRightAnswer = true,
                isSelectedIcon = false,
                onEvent = {},
                isClickable = false
            )
            IconNumberQuestion(
                numberQuestion = 2,
                value = QuestionStatus.CORRECT,
                showRightAnswer = true,
                isSelectedIcon = false,
                onEvent = {},
                isClickable = false
            )
            IconNumberQuestion(
                numberQuestion = 3,
                value = QuestionStatus.UNRESOLVED,
                showRightAnswer = true,
                isSelectedIcon = true,
                onEvent = {},
                isClickable = false
            )
            IconNumberQuestion(
                numberQuestion = 4,
                value = QuestionStatus.UNRESOLVED,
                showRightAnswer = true,
                isSelectedIcon = false,
                onEvent = {},
                isClickable = false
            )
        }
        AppTheme(true) {
            Row {
                IconNumberQuestion(
                    numberQuestion = 1,
                    value = QuestionStatus.ERROR,
                    showRightAnswer = true,
                    isSelectedIcon = false,
                    onEvent = {},
                    isClickable = false
                )
                IconNumberQuestion(
                    numberQuestion = 2,
                    value = QuestionStatus.CORRECT,
                    showRightAnswer = true,
                    isSelectedIcon = false,
                    onEvent = {},
                    isClickable = false
                )
                IconNumberQuestion(
                    numberQuestion = 3,
                    value = QuestionStatus.UNRESOLVED,
                    showRightAnswer = true,
                    isSelectedIcon = true,
                    onEvent = {},
                    isClickable = false
                )
                IconNumberQuestion(
                    numberQuestion = 4,
                    value = QuestionStatus.UNRESOLVED,
                    showRightAnswer = true,
                    isSelectedIcon = false,
                    onEvent = {},
                    isClickable = false
                )
            }
        }
    }
}