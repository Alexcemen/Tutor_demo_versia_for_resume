package ru.project.tutor.ui.screen.test_manager

import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState
import ru.project.tutor.domain.models.question_with_answers.QuestionWithAnswersData
import ru.project.tutor.domain.models.test.TestData
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.ui.screen.test_manager.models.QuestionInfoCardHighlightTextUi

object TestManagerStore {
    data class State(
        val testData: TestData? = null,
        val testId: Int = 0,
        val allQuestions: List<QuestionWithAnswersData> = emptyList(),
        val visibleQuestions: List<QuestionWithAnswersData> = emptyList(),
        val searchQuery: String = "",
        val questionEditBottomSheetId: Int = -1,
        val isVisibleBottomSheet: Boolean = false,
        val notificationAboutDeleteBottomSheetId: Int = -1,
        val noQuestionsNotificationBottomSheetId: Int = -1,
        val settingsBottomSheetId: Int = -1,
        val showSearchField: Boolean = false,
    ) : MviState

    data class UiState(
        val testName: String,
        val testId: Int,
        val imageId: Int,
        val colorId: Int,
        val visibleQuestions: List<QuestionInfoCardHighlightTextUi>,
        val searchQuery: String,
        val isVisibleBottomSheet: Boolean,
        val questionEditBottomSheetId: Int,
        val isVisibleNotificationAboutDeleteBottomSheet: Boolean,
        val notificationAboutDeleteBottomSheet: Int,
        val isVisibleNoQuestionsNotificationBottomSheet: Boolean,
        val isVisibleSettingsBottomSheet: Boolean,
        val visibleContent: Boolean,
        val showSearchField: Boolean = false,
    ) : MviUiState

    sealed interface SideEffect : MviSideEffect {
        data object Close : SideEffect
        data class OpenQuestionFactory(val testId: Int, val questionId: Int) : SideEffect
        data class StartTesting(val testId: Int, val testsListType: TestsListType) : SideEffect
    }

    sealed interface Event : MviEvent {
        data object Close : Event
        data object CreateQuestion : Event
        data object CloseQuestionEditBottomSheet : Event
        data object CloseNotificationAboutDeleteBottomSheet : Event
        data object CloseNoQuestionsNotificationBottomSheet : Event
        data object CloseSettingsBottomSheet : Event
        data class OpenQuestionActionsBottomSheet(val questionId: Int) : Event
        data class OpenNotificationAboutDeleteBottomSheet(val testId: Int) : Event
        data class OpenNoQuestionsNotificationBottomSheet(val testId: Int) : Event
        data object OpenSettingsBottomSheet : Event
        data class OpenQuestionEdit(val questionId: Int) : Event
        data class DeleteQuestion(val questionId: Int) : Event
        data object DeleteTest : Event
        data object Reload : Event
        data object ShareTest : Event
        data object StartTesting : Event
        data object OpenSearchField : Event
        data object CloseSearchField : Event
        data class SearchQueryChanged(val query: String) : Event
    }

    sealed interface Effect : MviEffect {
        data class LoadScreen(
            val testData: TestData,
            val questions: List<QuestionWithAnswersData>,
        ) : Effect

        data class VisibleQuestionEditBottomSheet(val questionId: Int) : Effect
        data class VisibleNotificationAboutDeleteBottomSheet(val testId: Int) : Effect
        data class VisibleNoQuestionsNotificationBottomSheet(val testId: Int) : Effect
        data class VisibleSettingBottomSheet(val testId: Int) : Effect
        data class DeleteQuestion(val questionId: Int) : Effect
        data class DeleteTest(val testId: Int) : Effect
        data class ToggleShowSearchField(val showSearchField: Boolean) : Effect
        data object ClearSearch : Effect
        data class UpdateSearchResult(
            val query: String,
            val filteredQuestions: List<QuestionWithAnswersData>,
        ) : Effect
    }
}