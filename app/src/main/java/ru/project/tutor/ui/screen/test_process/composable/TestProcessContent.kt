package ru.project.tutor.ui.screen.test_process.composable

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ru.project.tutor.R
import ru.project.tutor.ads.AppBannerAdLoader
import ru.project.tutor.common_ui.composable.elements.BottomSpacerSystem
import ru.project.tutor.common_ui.composable.elements.ContainerContent
import ru.project.tutor.common_ui.composable.elements.PrimaryButton
import ru.project.tutor.common_ui.composable.elements.SecondaryButton
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.domain.models.question.QuestionData
import ru.project.tutor.ui.models.AnswerChoiceDataUi
import ru.project.tutor.ui.models.QuestionForTestProcessUi
import ru.project.tutor.ui.screen.test_process.TestProcessStore
import ru.project.tutor.utils.SettingsConsts


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestProcessContent(
    state: TestProcessStore.UiState,
    onEvent: (TestProcessStore.Event) -> Unit,
    bannerAdLoader: AppBannerAdLoader? = null,
) {
    val currentQuestion = state.questions.getOrNull(state.numberSelectedQuestion - 1)

    BackHandler {
        onEvent(TestProcessStore.Event.ConfirmExitBack)
    }

    ContainerContent(
        topBar = {
            MainToolbarTestProcess(
                text = "Вопрос ${state.numberSelectedQuestion}",
                durationTesting = state.durationTesting,
                isFavorite = currentQuestion?.isFavorite == true,
                showFavorite = state.showFavorite,
                onEvent = onEvent
            )
        },
        background = AppTheme.colors.background.basic
    ) {
        Box(
            Modifier.fillMaxWidth()
        ) {
            LaunchedEffect(bannerAdLoader) {
                if (bannerAdLoader != null) {
                    while (true) {
                        delay(SettingsConsts.BANNER_RELOAD_INTERVAL_MS)
                        bannerAdLoader.reloadBanner()
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        if (bannerAdLoader != null) {
                            Modifier.padding(bottom = 60.dp)
                        } else {
                            Modifier
                        }
                    ),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    SpacerHeight(8.dp)
                    RowIconsNumberQuestion(
                        numberSelectedQuestion = state.numberSelectedQuestion,
                        questions = state.questions,
                        showRightAnswer = state.showRightAnswerOption,
                        isClickable = state.isClickableIconQuestion,
                        onEvent = onEvent
                    )
                    SpacerHeight(16.dp)
                    if (currentQuestion != null) {
                        QuestionWithAnswers(
                            showRightAnswer = state.showRightAnswerOption,
                            questionUi = currentQuestion,
                            doNotMarkMultipleAnswerChoice = state.tagMultipleAnswerChoice,
                            onEvent = onEvent
                        )
                    }
                }
            }
            if (currentQuestion?.isQuestionAnsweredBefore == true) {
                PrimaryButton(
                    text = stringResource(R.string.continue_button),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(
                            bottom = if (bannerAdLoader != null) {
                                WindowInsets.navigationBars.asPaddingValues()
                                    .calculateBottomPadding() + 84.dp
                            } else {
                                WindowInsets.navigationBars.asPaddingValues()
                                    .calculateBottomPadding() + 24.dp
                            }
                        )
                ) {
                    onEvent(TestProcessStore.Event.NextQuestion)
                }
            } else {
                SecondaryButton(
                    text = stringResource(R.string.to_answer_button),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(
                            bottom = if (bannerAdLoader != null) {
                                WindowInsets.navigationBars.asPaddingValues()
                                    .calculateBottomPadding() + 84.dp
                            } else {
                                WindowInsets.navigationBars.asPaddingValues()
                                    .calculateBottomPadding() + 24.dp
                            }
                        )
                ) {
                    onEvent(TestProcessStore.Event.CheckAnswerQuestion)
                }
            }
            SpacerHeight(16.dp)
            BottomSpacerSystem()

            if (bannerAdLoader != null) {
                YandexBannerAd(
                    adLoader = bannerAdLoader,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(
                            bottom = WindowInsets.navigationBars.asPaddingValues()
                                .calculateBottomPadding()
                        )
                )
            }
        }
        ConfirmExitTestBottomSheet(
            isVisibleBottomSheet = state.showConfirmExitBottomSheet,
            onDismiss = { onEvent(TestProcessStore.Event.CloseConfirmExitBottomSheet) },
            onEvent = onEvent
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ContentPreview() {
    AppTheme(true) {
        TestProcessContent(
            state = TestProcessStore.UiState(
                numberSelectedQuestion = 1,
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
                        isQuestionAnsweredBefore = true,
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
                        isFavorite = false,
                        isMultipleAnswerChoice = false
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
                        isQuestionAnsweredBefore = true,
                        isFavorite = false,
                        isMultipleAnswerChoice = false
                    )
                ),
                showRightAnswerOption = true,
                durationTesting = 30,
                tagMultipleAnswerChoice = false,
                showFavorite = true,
                isClickableIconQuestion = true,
                showConfirmExitBottomSheet = false
            ),
            onEvent = {}
        )
    }
}