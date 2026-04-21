package ru.project.tutor.ui.screen.test_info.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.BuildConfig
import ru.project.tutor.R
import ru.project.tutor.analytics.AppAnalytics
import ru.project.tutor.common_ui.composable.elements.ContainerContent
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.elements.TopBarSpacer
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.models.CountQuestionsInTestUi
import ru.project.tutor.ui.models.TestShortUi
import ru.project.tutor.ui.screen.test_info.TestInfoStore
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.ui.screen.test_info.composable.models.UserStatisticsUi

@Composable
@Preview
fun TestInfoContentPreview() {
    TestInfoContent(
        state = TestInfoStore.UiState(
            testList = listOf(
                TestShortUi(
                    id = 1,
                    title = "test",
                    colorId = 1,
                    imageId = 1,
                )
            ),
            countQuestions = CountQuestionsInTestUi(
                normalQuestionsCount = 1,
                favoriteQuestionsCount = 2,
                errorQuestionsCount = 3
            ),
            isVisibleBottomSheet = false,
            userStatisticsUi = UserStatisticsUi(
                firstLaunchDate = "12.12.2024",
                allCountTestCreated = 0,
                allCountErrors = 3,
                allCountTestsCompleted = 3,
                allCountTime = "12ч"
            ),
            currentPage = 1,
            showTelegramBanner = true,
            showAdExplanationBanner = true
        ),
        onEvent = {})
}

@Composable
@Preview
fun TestInfoContentEmptyPreview() {
    TestInfoContent(
        state = TestInfoStore.UiState(
            testList = listOf(),
            countQuestions = CountQuestionsInTestUi(
                normalQuestionsCount = 1,
                favoriteQuestionsCount = 2,
                errorQuestionsCount = 3
            ),
            isVisibleBottomSheet = false,
            userStatisticsUi = UserStatisticsUi(
                firstLaunchDate = "12.12.2024",
                allCountTestCreated = 0,
                allCountErrors = 3,
                allCountTestsCompleted = 3,
                allCountTime = "12ч"
            ),
            currentPage = 1,
            showTelegramBanner = true,
            showAdExplanationBanner = true
        ),
        onEvent = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestInfoContent(
    state: TestInfoStore.UiState,
    onEvent: (TestInfoStore.Event) -> Unit,
) {
    ContainerContent(applyHorizontalPadding = false) {
        TopBarSpacer()
        SelectActionForTestBottomSheet(
            isVisibleBottomSheet = state.isVisibleBottomSheet,
            onEvent = onEvent
        )
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Text(
                text = stringResource(R.string.test_info_title),
                style = AppTheme.textStyle.largeTitleOne,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp, end = 16.dp)
            )
            TestStatisticsBlock(state.userStatisticsUi)
            SpacerHeight(40.dp)
            if (state.showTelegramBanner) {
                TelegramBannerCard(
                    onClose = {
                        AppAnalytics.telegramBannerClosed()
                        onEvent(TestInfoStore.Event.CloseTelegramBanner)
                    },
                    onClick = {
                        AppAnalytics.telegramBannerClicked()
                        onEvent(TestInfoStore.Event.ClickTelegramBanner)
                    }
                )
                SpacerHeight(40.dp)
            } else if (state.showAdExplanationBanner) {
                AdExplanationBannerCard(
                    onClose = {
                        AppAnalytics.adExplanationBannerClosed()
                        onEvent(TestInfoStore.Event.CloseAdExplanationBanner)
                    },
                    onClick = {
                        AppAnalytics.adExplanationBannerClicked()
                        onEvent(TestInfoStore.Event.ClickAdExplanationBanner)
                    }
                )
                SpacerHeight(40.dp)
            }
            if (state.testList.isNotEmpty()) {
                MyTestBlock(
                    testList = state.testList,
                    currentPage = state.currentPage,
                    onEvent = onEvent
                )
            } else {
                MyListEmptyBlock { onEvent(TestInfoStore.Event.ClickCreateTestEmptyTest) }
            }
            SpacerHeight(40.dp)
            TestMenuBlock(
                countQuestionsInTestUi = state.countQuestions,
                onClickCreateTest = {
                    onEvent(
                        TestInfoStore.Event.OpenCreateNewTest
                    )
                },
                onClickMyTests = {
                    onEvent(
                        TestInfoStore.Event.OpenTestsList(
                            testsListType = TestsListType.NORMAL
                        )
                    )
                },
                onClickFavoriteQuestions = {
                    onEvent(
                        TestInfoStore.Event.OpenTestsList(
                            testsListType = TestsListType.FAVORITE
                        )
                    )
                },
                onClickErrors = {
                    onEvent(
                        TestInfoStore.Event.OpenTestsList(
                            testsListType = TestsListType.ERRORS
                        )
                    )
                }
            )
            if (BuildConfig.DEBUG) {
                SpacerHeight(40.dp)
            }
        }
    }
}