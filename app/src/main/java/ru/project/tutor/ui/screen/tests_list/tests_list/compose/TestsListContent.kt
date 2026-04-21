package ru.project.tutor.ui.screen.tests_list.tests_list.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.ContainerContent
import ru.project.tutor.common_ui.composable.elements.MainToolbar
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.domain.models.test.TestData
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.ui.screen.test_info.composable.TestCoverCard
import ru.project.tutor.ui.screen.tests_list.tests_list.TestsListStore


@Preview
@Composable
private fun TestsListContentPreview() {
    TestsListContent(
        state = TestsListStore.UiState(
            testList = listOf(
                TestData(
                    1,
                    "Супер мега длинное название теста",
                    1,
                    1,
                    dateLastTake = 0,
                    dateCreation = 0
                ),
                TestData(
                    2,
                    "dsf",
                    2,
                    2,
                    dateLastTake = 0,
                    dateCreation = 0
                ),
                TestData(
                    3,
                    "dsf",
                    3,
                    3,
                    dateLastTake = 0,
                    dateCreation = 0
                ),
            ),
            testsListType = TestsListType.NORMAL,
            isVisibleBottomSheet = false,
            bottomSheetId = 1,
            isVisibleNotificationBottomSheet = false,
            notificationBottomSheetId = 1,
            questionsCountMap = mapOf(
                1 to 10,
                2 to 5,
                3 to 8
            ),
            isLoading = false,
        ), {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceType")
@Composable
fun TestsListContent(
    state: TestsListStore.UiState,
    onEvent: (TestsListStore.Event) -> Unit,
) {
    val title = when (state.testsListType) {
        TestsListType.NORMAL -> stringResource(R.string.test_info_test_title_block)
        TestsListType.FAVORITE -> stringResource(R.string.favorite_questions_tool_bar)
        TestsListType.ERRORS -> stringResource(R.string.tittle_errors)
    }
    ContainerContent(
        topBar = {
            MainToolbar(
                text = title,
                isVisibleBack = true,
                onClickBack = {
                    onEvent(
                        TestsListStore.Event.Close
                    )
                }
            )
        }
    ) {
        if (state.isVisibleBottomSheet) {
            SelectActionForTestBottomSheet(
                isVisibleBottomSheet = true,
                testsListType = state.testsListType,
                state = rememberModalBottomSheetState(),
                onEvent = {
                    onEvent(it)
                }
            )
        }

        if (state.isVisibleNotificationBottomSheet) {
            EmptyListQuestionsNotificationBottomSheet(
                isVisibleBottomSheet = true,
                testsListType = state.testsListType,
                state = rememberModalBottomSheetState(),
                onEvent = {
                    onEvent(it)
                }
            )
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (
            (state.questionsCountMap.values.all { it == 0 } && state.testsListType != TestsListType.NORMAL)
            || (state.questionsCountMap.keys.isEmpty() && state.testsListType == TestsListType.NORMAL)
        ) {
            EmptyTestsScreen(
                type = state.testsListType,
                onEvent = onEvent
            )
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.testList) {
                    val countQuestions = state.questionsCountMap[it.id] ?: 0
                    if (state.testsListType == TestsListType.NORMAL || countQuestions > 0) {
                        TestCoverCard(
                            title = it.title,
                            iconId = it.imageId,
                            colorId = it.colorId,
                            countQuestions = state.questionsCountMap[it.id] ?: 0,
                            testsListType = state.testsListType,
                            onClick = {
                                onEvent(
                                    TestsListStore.Event.OpenQuestionActionBottomSheet(
                                        testId = it.id
                                    )
                                )
                            }
                        )
                        SpacerHeight(8.dp)
                    }
                }
            }
        }
    }
}