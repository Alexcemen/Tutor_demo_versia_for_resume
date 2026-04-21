package ru.project.tutor.ui.screen.test_manager.composable.question_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.SpacerWidth
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.screen.test_manager.models.AnswerChoiceHighlightTextUi

@Composable
@Preview(showBackground = true)
private fun AnswerChoicePreview() {
    AnswerChoice(
        AnswerChoiceHighlightTextUi(
            id = 1,
            highlightText = AnnotatedString("Наука"),
            isRightAnswer = false
        ),
    )
}

@Composable
fun AnswerChoice(
    state: AnswerChoiceHighlightTextUi,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 4.dp, end = 24.dp)
    ) {
        if (state.isRightAnswer) {
            Image(
                painter = painterResource(R.drawable.ic_correct_choice),
                contentDescription = "",
                colorFilter = ColorFilter.tint(AppTheme.colors.background.green),
                modifier = Modifier.size(16.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.ic_incorrect_choice),
                contentDescription = "",
                colorFilter = ColorFilter.tint(AppTheme.colors.background.red),
                modifier = Modifier.size(16.dp)
            )
        }
        SpacerWidth(8.dp)
        Text(
            text = state.highlightText,
            style = AppTheme.textStyle.captionTwo,
            color = AppTheme.colors.text.primary
        )
    }
}