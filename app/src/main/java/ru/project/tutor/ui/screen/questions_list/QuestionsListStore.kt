package ru.project.tutor.ui.screen.questions_list

import ru.project.tutor.common_ui.composable.mvi.MviEffect
import ru.project.tutor.common_ui.composable.mvi.MviEvent
import ru.project.tutor.common_ui.composable.mvi.MviSideEffect
import ru.project.tutor.common_ui.composable.mvi.MviState
import ru.project.tutor.common_ui.composable.mvi.MviUiState
import ru.project.tutor.domain.models.question_with_answers.QuestionWithAnswersData
import ru.project.tutor.ui.screen.test_info.TestsListType

object QuestionsListStore {

    data class State(
        val questionsList: List<QuestionWithAnswersData> = emptyList(),
        val testsListType: TestsListType = TestsListType.NORMAL,
    ) : MviState

    data class UiState(
        val questionsList: List<QuestionWithAnswersData>,
        val testsListType: TestsListType,
    ) : MviUiState

    sealed interface SideEffect : MviSideEffect {
        data object Close : SideEffect
    }

    sealed interface Event : MviEvent {
        data object Close : Event
        data class UpdateQuestions(
            val testsListType: TestsListType,
            val questions: List<QuestionWithAnswersData>,
        ) : Event
    }

    sealed interface Effect : MviEffect {
        data class UpdateQuestions(
            val testsListType: TestsListType,
            val questions: List<QuestionWithAnswersData>,
        ) : Effect
    }

}