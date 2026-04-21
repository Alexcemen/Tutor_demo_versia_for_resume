package ru.project.tutor.ui.screen.questions_list.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.ContainerContent
import ru.project.tutor.common_ui.composable.elements.MainToolbar
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.domain.models.question.QuestionData
import ru.project.tutor.domain.models.question_with_answers.QuestionWithAnswersData
import ru.project.tutor.ui.models.AnswerChoiceUi
import ru.project.tutor.ui.models.QuestionInfoCardUi
import ru.project.tutor.ui.screen.questions_list.QuestionsListStore
import ru.project.tutor.ui.screen.test_info.TestsListType

@Preview
@Composable
private fun QuestionsListContentPreview() {
    QuestionsListContent(
        state = QuestionsListStore.UiState(
            questionsList = listOf(
                QuestionWithAnswersData(
                    QuestionData(
                        id = 1,
                        questionText = "Что такое математика?",
                        testId = 1,
                        position = 1,
                        isMultipleAnswerChoice = false
                    ),
                    listOf(
                        AnswerChoiceData(
                            id = 1,
                            text = "Наука",
                            isRightAnswer = false,
                            questionId = 1
                        ),
                        AnswerChoiceData(
                            id = 2,
                            text = "Наука",
                            isRightAnswer = true,
                            questionId = 1
                        ),
                        AnswerChoiceData(
                            id = 3,
                            text = "Наука",
                            isRightAnswer = false,
                            questionId = 1
                        ),
                        AnswerChoiceData(
                            id = 4,
                            text = "Наука",
                            isRightAnswer = false,
                            questionId = 1
                        )
                    )
                ),
                QuestionWithAnswersData(
                    QuestionData(
                        id = 2,
                        questionText = "Что такое математика?",
                        testId = 1,
                        position = 1,
                        isMultipleAnswerChoice = false
                    ),
                    listOf(
                        AnswerChoiceData(
                            id = 1,
                            text = "Наука",
                            isRightAnswer = false,
                            questionId = 2
                        ),
                        AnswerChoiceData(
                            id = 2,
                            text = "Наука",
                            isRightAnswer = false,
                            questionId = 2
                        ),
                        AnswerChoiceData(
                            id = 3,
                            text = "Наука",
                            isRightAnswer = false,
                            questionId = 2
                        ),
                        AnswerChoiceData(
                            id = 4,
                            text = "Наука",
                            isRightAnswer = true,
                            questionId = 2
                        )
                    )
                )
            ),
            testsListType = TestsListType.FAVORITE
        ),
        onEvent = {}
    )
}


@Composable
fun QuestionsListContent(
    state: QuestionsListStore.UiState,
    onEvent: (QuestionsListStore.Event) -> Unit,
) {
    ContainerContent(
        topBar = {
            MainToolbar(
                text = when (state.testsListType) {
                    TestsListType.NORMAL -> stringResource(R.string.my_test_block_title)
                    TestsListType.FAVORITE -> stringResource(R.string.tittle_favorite_questions)
                    TestsListType.ERRORS -> stringResource(R.string.tittle_errors)
                },
                isVisibleBack = true,
                onClickBack = {
                    onEvent(
                        QuestionsListStore.Event.Close
                    )
                }
            )
        },
        background = AppTheme.colors.background.basic
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(state.questionsList) { index, element ->
                QuestionInfoCard(
                    state = QuestionInfoCardUi(
                        questionId = element.question.id,
                        position = element.question.position,
                        questionNumber = index + 1,
                        questionText = element.question.questionText,
                        answerChoices = element.answers.map {
                            AnswerChoiceUi(
                                id = it.id,
                                text = it.text,
                                isRightAnswer = it.isRightAnswer
                            )
                        }
                    )
                )
                SpacerHeight(24.dp)
            }
        }
    }
}