package ru.project.tutor.ui.screen.questions_list

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.domain.repository.ErrorRepository
import ru.project.tutor.domain.repository.FavoriteRepository
import ru.project.tutor.domain.repository.QuestionRepository
import ru.project.tutor.ui.screen.test_info.TestsListType

class QuestionsListViewModel constructor(
    reducer: QuestionsListReducer,
    private val questionRepository: QuestionRepository,
    private val favoriteRepository: FavoriteRepository,
    private val errorRepository: ErrorRepository,
    private val testId: Int,
    private val testsListType: TestsListType,
) : ScreenViewModel<QuestionsListStore.State, QuestionsListStore.Event, QuestionsListStore.SideEffect, QuestionsListStore.Effect, QuestionsListStore.UiState>(
    reducer
) {

    init {
        viewModelScope.launch {
            val questions = when (testsListType) {
                TestsListType.NORMAL -> questionRepository.getQuestionsWithAnswersByTestId(testId)
                TestsListType.FAVORITE -> favoriteRepository.getFavoritesByTestId(testId).first()
                    .mapNotNull { questionRepository.getQuestionWithAnswersByQuestionId(it.questionId) }

                TestsListType.ERRORS -> errorRepository.getErrorsByTestId(testId).first()
                    .mapNotNull { questionRepository.getQuestionWithAnswersByQuestionId(it.questionId) }
            }
            onEvent(
                QuestionsListStore.Event.UpdateQuestions(
                    testsListType = testsListType,
                    questions = questions
                )
            )
        }
    }

    override fun createState(): QuestionsListStore.State = QuestionsListStore.State()

    override fun handleEvent(
        currentState: QuestionsListStore.State,
        intent: QuestionsListStore.Event,
    ): Flow<QuestionsListStore.Effect> = when (intent) {
        QuestionsListStore.Event.Close -> flow {
            sendSideEffect(QuestionsListStore.SideEffect.Close)
        }

        is QuestionsListStore.Event.UpdateQuestions -> flow {
            emit(
                QuestionsListStore.Effect.UpdateQuestions(
                    testsListType = intent.testsListType,
                    questions = intent.questions
                )
            )
        }
    }


    override fun handleEffect(
        currentState: QuestionsListStore.State,
        effect: QuestionsListStore.Effect,
    ): QuestionsListStore.State {
        return when (effect) {
            is QuestionsListStore.Effect.UpdateQuestions -> currentState.copy(
                testsListType = effect.testsListType,
                questionsList = effect.questions
            )
        }
    }
}