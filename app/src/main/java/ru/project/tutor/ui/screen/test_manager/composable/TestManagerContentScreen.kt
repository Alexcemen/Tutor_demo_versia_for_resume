package ru.project.tutor.ui.screen.test_manager.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.BottomSpacerSystem
import ru.project.tutor.common_ui.composable.elements.ContainerContent
import ru.project.tutor.common_ui.composable.elements.MainToolbar
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.screen.test_manager.TestManagerStore
import ru.project.tutor.ui.screen.test_manager.composable.question_info.QuestionInfoCard
import ru.project.tutor.ui.screen.test_manager.models.AnswerChoiceHighlightTextUi
import ru.project.tutor.ui.screen.test_manager.models.QuestionInfoCardHighlightTextUi

@Composable
@Preview
private fun TestManagerContentPreview() {
    TestManagerContent(
        state = TestManagerStore.UiState(
            testId = 0,
            testName = "qwerty",
            imageId = 1,
            colorId = 1,
            visibleQuestions = listOf(
                QuestionInfoCardHighlightTextUi(
                    questionId = 1,
                    questionText = AnnotatedString("Что такое математика?"),
                    position = 1,
                    questionNumber = 1,
                    answerChoices = listOf(
                        AnswerChoiceHighlightTextUi(
                            id = 1,
                            highlightText = AnnotatedString("Наука"),
                            isRightAnswer = false,
                        )
                    )
                )
            ),
            isVisibleBottomSheet = false,
            questionEditBottomSheetId = -1,
            isVisibleNotificationAboutDeleteBottomSheet = false,
            notificationAboutDeleteBottomSheet = -1,
            visibleContent = true,
            isVisibleNoQuestionsNotificationBottomSheet = false,
            isVisibleSettingsBottomSheet = false,
            showSearchField = false,
            searchQuery = ""
        )
    ) {}
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceType")
@Composable
fun TestManagerContent(
    state: TestManagerStore.UiState,
    onEvent: (TestManagerStore.Event) -> Unit,
) {
    val questionEditSheetState = rememberModalBottomSheetState()
    val deleteNotificationSheetState = rememberModalBottomSheetState()
    val noQuestionsNotificationBottomSheetState = rememberModalBottomSheetState()
    val settingBottomSheetState = rememberModalBottomSheetState()

    ContainerContent(
        topBar = {
            MainToolbar(
                text = stringResource(R.string.tittle_test_manager),
                isVisibleBack = true,
                onClickBack = {
                    onEvent(
                        TestManagerStore.Event.Close
                    )
                }
            )
        },
        background = AppTheme.colors.background.basic
    ) {
        if (state.isVisibleBottomSheet) {
            QuestionEditBottomSheet(
                isVisibleBottomSheet = true,
                state = questionEditSheetState,
                questionEditBottomSheetId = state.questionEditBottomSheetId,
                onEvent = { onEvent(it) }
            )
        }

        if (state.isVisibleNotificationAboutDeleteBottomSheet) {
            NotificationAboutDeleteBottomSheet(
                isVisibleNotificationAboutDeleteBottomSheet = true,
                state = deleteNotificationSheetState,
                notificationAboutDeleteBottomSheetId = state.notificationAboutDeleteBottomSheet,
                onEvent = { onEvent(it) }
            )
        }

        if (state.isVisibleNoQuestionsNotificationBottomSheet) {
            EmptyListQuestionsNotificationBottomSheet(
                isVisibleBottomSheet = true,
                state = noQuestionsNotificationBottomSheetState,
                onEvent = {
                    onEvent(it)
                }
            )
        }

        if (state.isVisibleSettingsBottomSheet) {
            SettingBottomSheet(
                isVisibleBottomSheet = true,
                state = settingBottomSheetState,
                onEvent = {
                    onEvent(it)
                }
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                if (state.visibleContent) {
                    SpacerHeight(8.dp)

                    val listState = rememberLazyListState()
                    val scrollOffset = listState.firstVisibleItemScrollOffset

                    val cardAlpha = (1f - scrollOffset / 400f).coerceIn(0f, 1f)
                    val cardScale = (1f - scrollOffset / 600f).coerceIn(0f, 1f)

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = listState
                    ) {
                        item {
                            TestCoverCardTestManager(
                                title = state.testName,
                                iconId = state.imageId,
                                colorId = state.colorId,
                                modifier = Modifier
                                    .graphicsLayer {
                                        alpha = cardAlpha
                                        scaleX = cardScale
                                        scaleY = cardScale
                                    },
                            ) {}
                            SpacerHeight(16.dp)
                        }

                        item {
                            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = pluralStringResource(
                                            id = R.plurals.count_questions,
                                            count = state.visibleQuestions.size,
                                            state.visibleQuestions.size
                                        ),
                                        style = AppTheme.textStyle.subtitleTwo,
                                        color = AppTheme.colors.text.primary,
                                    )
                                    IconButton(
                                        modifier = Modifier
                                            .size(24.dp),
                                        onClick = {
                                            onEvent(
                                                TestManagerStore.Event.CreateQuestion
                                            )
                                        }
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.ic_plus),
                                            contentDescription = "",
                                            colorFilter = ColorFilter.tint(AppTheme.colors.background.primary),
                                        )
                                    }
                                }
                                SpacerHeight(16.dp)
                            }
                        }
                        itemsIndexed(state.visibleQuestions) { index, question ->
                            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                QuestionInfoCard(
                                    state = question,
                                    onClick = {
                                        onEvent(
                                            TestManagerStore.Event.OpenQuestionActionsBottomSheet(
                                                questionId = question.questionId
                                            )
                                        )
                                    }
                                )
                                SpacerHeight(24.dp)
                                if (index == state.visibleQuestions.lastIndex) {
                                    SpacerHeight((16 + 24).dp)
                                }
                            }
                        }
                    }
                }
                BottomToolBar(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 16.dp),
                    showSearchField = state.showSearchField,
                    searchQuery = state.searchQuery,
                    onSearchQueryChange = { query ->
                        onEvent(TestManagerStore.Event.SearchQueryChanged(query))
                    },
                    onEvent = onEvent
                )
            }

            val imeBottom = WindowInsets.ime.asPaddingValues().calculateBottomPadding()
            if (imeBottom.value == 0F) {
                BottomSpacerSystem()
            } else {
                Spacer(
                    Modifier.height(imeBottom)
                )
            }
        }
    }
}
