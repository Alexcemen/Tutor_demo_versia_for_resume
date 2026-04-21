package ru.project.tutor.ui.screen.test_result.compose

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ru.project.tutor.R
import ru.project.tutor.ads.AppTestResultBannerAdLoader
import ru.project.tutor.common_ui.composable.elements.ContainerContent
import ru.project.tutor.common_ui.composable.elements.MainToolbar
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.domain.models.question.QuestionData
import ru.project.tutor.ui.models.AnswerChoiceDataUi
import ru.project.tutor.ui.models.QuestionWorkoutUi
import ru.project.tutor.ui.screen.test_result.TestResultStore
import ru.project.tutor.ui.screen.test_result.compose.QuestionStatusTestResult.CORRECT
import ru.project.tutor.utils.SettingsConsts

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestResultContentPreview() {
    AppTheme(true) {
        TestResultContent(
            state = TestResultStore.UiState(
                isErrorDialogVisible = false,
                isDontShowAgainCheckbox = false,
                answerFilter = TestResultStore.AnswerFilter.ALL,
                questions = listOf(
                    QuestionWorkoutUi(
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
                        questionStatus = QuestionStatusTestResult.CORRECT,
                    ),
                    QuestionWorkoutUi(
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
                        questionStatus = QuestionStatusTestResult.CORRECT,
                    ),
                    QuestionWorkoutUi(
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
                        questionStatus = QuestionStatusTestResult.CORRECT,
                    )
                ),
            ),
            onEvent = {}
        )
    }
}


@Composable
fun TestResultContent(
    state: TestResultStore.UiState,
    onEvent: (TestResultStore.Event) -> Unit,
    bannerAdLoader: AppTestResultBannerAdLoader? = null,
) {
    ContainerContent(
        topBar = {
            MainToolbar(
                text = stringResource(R.string.test_result_title),
                isVisibleBack = true,
                onClickBack = {
                    onEvent(TestResultStore.Event.Close)
                }
            )
        },
        background = AppTheme.colors.background.basic
    ) {
        val countRightAnswers = state.questions.count {
            it.questionStatus == CORRECT
        }
        if (state.questions.isEmpty() && state.answerFilter == TestResultStore.AnswerFilter.ALL) return@ContainerContent

        val countQuestions = state.questions.size

        val visibilityList = remember { mutableStateOf<List<Boolean>>(emptyList()) }

        LaunchedEffect(state.questions) {
            visibilityList.value = List(state.questions.size) { false }
            delay(100L)
            state.questions.forEachIndexed { index, _ ->
                delay(50L)
                visibilityList.value = visibilityList.value.toMutableList()
                    .also { it[index] = true }
            }
        }

        val currentVisibilityList = visibilityList.value

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            LaunchedEffect(bannerAdLoader) {
                if (bannerAdLoader != null) {
                    while (true) {
                        delay(SettingsConsts.BANNER_RELOAD_INTERVAL_MS)
                        bannerAdLoader.reloadBanner()
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item { SpacerHeight(12.dp) }
                item(key = "indicator") {
                    IndicatorTestResult(
                        indicatorValue = countRightAnswers,
                        maxIndicatorValue = countQuestions,
                        animateOnce = true
                    )
                }

                item { SpacerHeight(16.dp) }

                item {
                    AnswerFilterRow(
                        currentFilter = state.answerFilter,
                        onFilterSelected = { onEvent(TestResultStore.Event.SelectAnswerFilter(it)) }
                    )
                }

                itemsIndexed(state.questions, key = { _, q -> q.question.id }) { index, question ->
                    SpacerHeight(16.dp)

                    val alpha by animateFloatAsState(
                        targetValue = if (currentVisibilityList.getOrNull(index) == true) 1f else 0f,
                        animationSpec = androidx.compose.animation.core.tween(durationMillis = 300)
                    )

                    QuestionItemTestResult(
                        question = question,
                        modifier = Modifier
                            .graphicsLayer { this.alpha = alpha }
                            .fillMaxWidth()
                    )
                }

                item { SpacerHeight(16.dp) }
                item {
                    SpacerHeight(
                        if (bannerAdLoader != null) {
                            24.dp + WindowInsets.navigationBars.asPaddingValues()
                                .calculateBottomPadding()
                        } else {
                            WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                        }
                    )
                }
            }

            if (bannerAdLoader != null) {
                TestResultBannerAd(
                    adLoader = bannerAdLoader,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = WindowInsets.navigationBars.asPaddingValues()
                                .calculateBottomPadding()
                        )
                )
            }
        }
        if (state.isErrorDialogVisible) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                TooManyErrorsDialog(
                    isDontShowAgainCheckbox = state.isDontShowAgainCheckbox,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Composable
private fun AnswerFilterRow(
    currentFilter: TestResultStore.AnswerFilter,
    onFilterSelected: (TestResultStore.AnswerFilter) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AnswerFilterButton(
            text = stringResource(R.string.test_result_filter_all),
            isSelected = currentFilter == TestResultStore.AnswerFilter.ALL,
            onClick = { onFilterSelected(TestResultStore.AnswerFilter.ALL) },
            modifier = Modifier.weight(1f)
        )
        AnswerFilterButton(
            text = stringResource(R.string.test_result_filter_correct),
            isSelected = currentFilter == TestResultStore.AnswerFilter.CORRECT,
            onClick = { onFilterSelected(TestResultStore.AnswerFilter.CORRECT) },
            modifier = Modifier.weight(1f)
        )
        AnswerFilterButton(
            text = stringResource(R.string.test_result_filter_incorrect),
            isSelected = currentFilter == TestResultStore.AnswerFilter.INCORRECT,
            onClick = { onFilterSelected(TestResultStore.AnswerFilter.INCORRECT) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun AnswerFilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bgColor = if (isSelected) {
        AppTheme.colors.background.primary
    } else {
        Color.Transparent
    }
    val txtColor = if (isSelected) {
        AppTheme.colors.text.reverse
    } else {
        AppTheme.colors.text.primary
    }
    val borderColor = if (isSelected) {
        AppTheme.colors.background.primary
    } else {
        AppTheme.colors.background.secondary
    }

    Box(
        modifier = modifier
            .height(44.dp)
            .background(bgColor, RoundedCornerShape(12.dp))
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .noRippleClickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = txtColor,
            style = AppTheme.textStyle.captionTwo,
        )
    }
}
