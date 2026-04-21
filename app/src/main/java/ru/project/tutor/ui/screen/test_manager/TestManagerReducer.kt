package ru.project.tutor.ui.screen.test_manager

import ru.project.tutor.common_ui.composable.mvi.Reducer
import ru.project.tutor.ui.screen.test_manager.models.AnswerChoiceHighlightTextUi
import ru.project.tutor.ui.screen.test_manager.models.QuestionInfoCardHighlightTextUi

class TestManagerReducer() :
    Reducer<TestManagerStore.State, TestManagerStore.UiState> {
    override fun reduce(state: TestManagerStore.State): TestManagerStore.UiState {
        return TestManagerStore.UiState(
            testId = state.testData?.id ?: 0,
            testName = state.testData?.title ?: "",
            imageId = state.testData?.imageId ?: 1,
            colorId = state.testData?.colorId ?: 1,
            searchQuery = state.searchQuery,
            visibleQuestions = state.visibleQuestions.mapIndexed { index, question ->
                QuestionInfoCardHighlightTextUi(
                    questionId = question.question.id,
                    position = question.question.position,
                    questionNumber = index + 1,
                    questionText = highlightText(question.question.questionText, state.searchQuery),
                    answerChoices = question.answers.map { answer ->
                        AnswerChoiceHighlightTextUi(
                            id = answer.id,
                            highlightText = highlightText(answer.text, state.searchQuery),
                            isRightAnswer = answer.isRightAnswer
                        )
                    }
                )
            },
            isVisibleBottomSheet = state.questionEditBottomSheetId != -1,
            questionEditBottomSheetId = state.questionEditBottomSheetId,
            isVisibleNotificationAboutDeleteBottomSheet = state.notificationAboutDeleteBottomSheetId != -1,
            notificationAboutDeleteBottomSheet = state.notificationAboutDeleteBottomSheetId,
            isVisibleNoQuestionsNotificationBottomSheet = state.noQuestionsNotificationBottomSheetId != -1,
            isVisibleSettingsBottomSheet = state.settingsBottomSheetId != -1,
            visibleContent = state.testId != 0,
            showSearchField = state.showSearchField,
        )
    }
}