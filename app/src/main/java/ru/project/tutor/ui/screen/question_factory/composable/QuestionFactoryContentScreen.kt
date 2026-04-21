package ru.project.tutor.ui.screen.question_factory.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.AppCard
import ru.project.tutor.common_ui.composable.elements.BottomSpacerSystem
import ru.project.tutor.common_ui.composable.elements.ContainerContent
import ru.project.tutor.common_ui.composable.elements.MainToolbar
import ru.project.tutor.common_ui.composable.elements.PrimaryButton
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.models.AnswerChoiceUi
import ru.project.tutor.ui.screen.question_factory.QuestionFactoryStore

@Composable
@Preview
private fun QuestionFactoryContentScreenPreview() {
    QuestionFactoryContentScreen(
        QuestionFactoryStore.UiState(
            questionId = -1,
            question = "asd",
            testId = 1,
            isMultipleAnswerChoice = false,
            answerChoices = listOf(
                AnswerChoiceUi(
                    id = 1,
                    text = "Ответ 1",
                    isRightAnswer = false
                ),
                AnswerChoiceUi(
                    id = 2,
                    text = "Ответ 2",
                    isRightAnswer = false
                )
            ),
            isVisibleBottomSheet = false,
            bottomSheetMessage = "",
        ),
        {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionFactoryContentScreen(
    state: QuestionFactoryStore.UiState,
    onEvent: (QuestionFactoryStore.Event) -> Unit,
) {
    QuestionFactoryBottomSheet(
        isVisibleBottomSheet = state.isVisibleBottomSheet,
        text = state.bottomSheetMessage,
        onEvent = onEvent
    )
    ContainerContent(topBar = {
        MainToolbar(
            text = state.title,
            isVisibleBack = true,
            onClickBack = {
                onEvent(QuestionFactoryStore.Event.Back)
            }
        )
    }, background = AppTheme.colors.background.basic) {
        SpacerHeight(24.dp)
        Box(
            Modifier.fillMaxWidth()
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    containerColor = AppTheme.colors.background.secondaryTwo
                ) {
                    val enterQuestionHint = stringResource(R.string.enter_question_hint)

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        TextFieldEmpty(
                            value = state.question,
                            onValueChange = { onEvent(QuestionFactoryStore.Event.UpdateQuestion(it)) },
                            placeHolder = enterQuestionHint,
                            placeholderStyleFont = AppTheme.textStyle.subtitleOne
                        )
                    }
                }
                state.answerChoices.forEach { answer ->
                    SpacerHeight(16.dp)
                    AnswerItem(
                        answer = answer,
                        onValueChange = {
                            onEvent(QuestionFactoryStore.Event.UpdateAnswer(answer.id, it))
                        },
                        onRemove = {
                            onEvent(QuestionFactoryStore.Event.RemoveAnswer(answer.id))
                        },
                        onEvent = onEvent,
                    )
                }

                SpacerHeight(16.dp)
                IconButton(
                    onClick = { onEvent(QuestionFactoryStore.Event.AddAnswer) }
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_plus),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(AppTheme.colors.background.primary),
                        modifier = Modifier.size(33.dp)
                    )
                }
                BottomSpacerSystem()
                SpacerHeight(84.dp)
            }
            PrimaryButton(
                text = stringResource(R.string.question_factory_button),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        bottom = WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding() + 16.dp
                    )
            ) {
                onEvent(
                    QuestionFactoryStore.Event.Save
                )
            }
        }
    }
}




