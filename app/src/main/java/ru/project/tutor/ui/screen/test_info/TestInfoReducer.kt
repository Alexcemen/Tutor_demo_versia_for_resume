package ru.project.tutor.ui.screen.test_info

import ru.project.tutor.common_ui.composable.mvi.Reducer
import ru.project.tutor.ui.models.CountQuestionsInTestUi
import ru.project.tutor.ui.models.TestShortUi
import ru.project.tutor.ui.screen.test_info.composable.models.UserStatisticsUi

class TestInfoReducer() : Reducer<TestInfoStore.State, TestInfoStore.UiState> {
    override fun reduce(state: TestInfoStore.State): TestInfoStore.UiState {
        return TestInfoStore.UiState(
            testList = state.testList.map {
                TestShortUi(
                    id = it.testData.id,
                    title = it.testData.title,
                    colorId = it.testData.colorId,
                    imageId = it.testData.imageId,
                )
            },
            countQuestions = CountQuestionsInTestUi(
                normalQuestionsCount = state.testList.sumOf { it.normalQuestionsCount },
                favoriteQuestionsCount = state.testList.sumOf { it.favoriteQuestionsCount },
                errorQuestionsCount = state.testList.sumOf { it.errorQuestionsCount }
            ),
            currentPage = state.currentPage,
            isVisibleBottomSheet = state.testActionBottomSheetId != -1,
            userStatisticsUi = UserStatisticsUi(
                firstLaunchDate = state.firstLaunchDate,
                allCountTestCreated = state.allCountTestCreated,
                allCountErrors = state.allCountErrors,
                allCountTestsCompleted = state.allCountTestsCompleted,
                allCountTime = state.allCountTime
            ),
            showTelegramBanner = state.showTelegramBanner,
            showAdExplanationBanner = state.showAdExplanationBanner
        )
    }
}