package ru.project.tutor.ui.screen.test_process.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.composable.elements.SpacerWidth
import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.domain.models.question.QuestionData
import ru.project.tutor.ui.models.AnswerChoiceDataUi
import ru.project.tutor.ui.models.QuestionForTestProcessUi
import ru.project.tutor.ui.screen.test_process.TestProcessStore

@Composable
fun RowIconsNumberQuestion(
    numberSelectedQuestion: Int,
    questions: List<QuestionForTestProcessUi>,
    showRightAnswer: Boolean,
    isClickable: Boolean,
    onEvent: (TestProcessStore.Event) -> Unit,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(numberSelectedQuestion) {
        val targetIndex = numberSelectedQuestion - 1

        val visibleItemsCount = listState.layoutInfo.visibleItemsInfo.size
        if (visibleItemsCount == 0) return@LaunchedEffect

        val threshold = visibleItemsCount / 2
        val firstVisible = listState.firstVisibleItemIndex

        if (targetIndex < firstVisible) {
            listState.animateScrollToItem(targetIndex)
            return@LaunchedEffect
        }

        if (targetIndex < firstVisible + threshold) {
            val newIndex = (targetIndex - threshold).coerceAtLeast(0)
            listState.animateScrollToItem(newIndex)
            return@LaunchedEffect
        }

        if (targetIndex > firstVisible + threshold) {
            val newIndex = (targetIndex - threshold).coerceAtLeast(0)
            listState.animateScrollToItem(newIndex)
            return@LaunchedEffect
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(questions) { index, item ->
            val isSelectedIcon = numberSelectedQuestion - 1 == index
            IconNumberQuestion(
                numberQuestion = index + 1,
                value = item.questionStatus,
                showRightAnswer = showRightAnswer,
                isSelectedIcon = isSelectedIcon,
                isClickable = isClickable,
                onEvent = onEvent
            )
            SpacerWidth(16.dp)
        }
    }
}

@Preview
@Composable
private fun RowIconsNumberQuestionPreview() {
    RowIconsNumberQuestion(
        questions = listOf(
            QuestionForTestProcessUi(
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
                        )
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
                questionStatus = QuestionStatus.UNRESOLVED,
                isQuestionAnsweredBefore = false,
                isFavorite = true,
                isMultipleAnswerChoice = false
            ),
            QuestionForTestProcessUi(
                QuestionData(
                    id = 1,
                    questionText = "Что такое математика?",
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
                        )
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
                questionStatus = QuestionStatus.UNRESOLVED,
                isQuestionAnsweredBefore = false,
                isMultipleAnswerChoice = false,
                isFavorite = false
            ),
            QuestionForTestProcessUi(
                QuestionData(
                    id = 2,
                    questionText = "Что такое математика?",
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
                        )
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
                questionStatus = QuestionStatus.UNRESOLVED,
                isQuestionAnsweredBefore = false,
                isMultipleAnswerChoice = false,
                isFavorite = false
            )
        ),
        numberSelectedQuestion = 1,
        showRightAnswer = true,
        onEvent = {},
        isClickable = false
    )
}