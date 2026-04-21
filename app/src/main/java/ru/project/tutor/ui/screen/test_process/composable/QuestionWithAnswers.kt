package ru.project.tutor.ui.screen.test_process.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.composable.elements.BottomSpacerSystem
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.domain.models.question.QuestionData
import ru.project.tutor.ui.models.AnswerChoiceDataUi
import ru.project.tutor.ui.models.QuestionForTestProcessUi
import ru.project.tutor.ui.screen.test_process.TestProcessStore


@Composable
fun QuestionWithAnswers(
    showRightAnswer: Boolean,
    questionUi: QuestionForTestProcessUi,
    doNotMarkMultipleAnswerChoice: Boolean,
    onEvent: (TestProcessStore.Event) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn {
            item {
                Text(
                    text = questionUi.question.questionText,
                    style = AppTheme.textStyle.subtitleOne,
                    color = AppTheme.colors.text.primary
                )
                SpacerHeight(24.dp)
            }
            items(questionUi.answers) { answer ->
                AnswerItem(
                    answer = answer,
                    questionStatus = questionUi.questionStatus,
                    showRightAnswer = showRightAnswer,
                    isMultipleAnswerChoice = questionUi.isMultipleAnswerChoice,
                    doNotMarkMultipleAnswerChoice = doNotMarkMultipleAnswerChoice,
                    onEvent = onEvent
                )
                SpacerHeight(16.dp)
            }
            item {
                BottomSpacerSystem()
                SpacerHeight(82.dp)
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QuestionWithAnswersPreviewWorkout() {
    AppTheme(true) {
        QuestionWithAnswers(
            questionUi = QuestionForTestProcessUi(
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
                        ),
                        isSelectedAnswer = false
                    ),
                    AnswerChoiceDataUi(
                        AnswerChoiceData(
                            id = 3,
                            text = "2008",
                            isRightAnswer = false,
                            questionId = 1
                        ),
                        isSelectedAnswer = false
                    )
                ),
                questionStatus = QuestionStatus.ERROR,
                isQuestionAnsweredBefore = true,
                isFavorite = false,
                isMultipleAnswerChoice = true
            ),
            showRightAnswer = true,
            doNotMarkMultipleAnswerChoice = false,
            onEvent = {}
        )
    }
}