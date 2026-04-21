package ru.project.tutor.ui.screen.question_factory

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.mvi.ScreenViewModel
import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.domain.repository.AnswerChoiceRepository
import ru.project.tutor.domain.repository.QuestionRepository
import ru.project.tutor.utils.AppResource
import ru.project.tutor.utils.withIO

class QuestionFactoryViewModel constructor(
    reducer: QuestionFactoryReducer,
    private val questionRepository: QuestionRepository,
    private val answerChoiceRepository: AnswerChoiceRepository,
    private val appResource: AppResource,
    private val testId: Int,
    private val questionId: Int,
) : ScreenViewModel<QuestionFactoryStore.State, QuestionFactoryStore.Event, QuestionFactoryStore.SideEffect, QuestionFactoryStore.Effect, QuestionFactoryStore.UiState>(
    reducer
) {

    init {
        viewModelScope.launch {
            forceEffect(QuestionFactoryStore.Effect.LoadScreen(testId))

            if (questionId != -1) {
                onEvent(QuestionFactoryStore.Event.LoadQuestion(questionId))
            } else {
                onEvent(QuestionFactoryStore.Event.CreateNewQuestion)
            }
        }
    }

    override fun createState(): QuestionFactoryStore.State = QuestionFactoryStore.State()

    override fun handleEffect(
        currentState: QuestionFactoryStore.State,
        effect: QuestionFactoryStore.Effect,
    ): QuestionFactoryStore.State = when (effect) {
        is QuestionFactoryStore.Effect.UpdateText -> {
            currentState.copy(question = effect.question)
        }

        is QuestionFactoryStore.Effect.LoadScreen -> {
            currentState.copy(testId = effect.testId)
        }

        is QuestionFactoryStore.Effect.UpdateAnswerList -> {
            currentState.copy(answerChoice = effect.answers)
        }

        is QuestionFactoryStore.Effect.VisibleBottomSheet ->
            currentState.copy(
                isVisibleBottomSheet = effect.testId != -1,
                bottomSheetMessage = effect.message.ifEmpty { currentState.bottomSheetMessage }
            )

        is QuestionFactoryStore.Effect.UpdateQuestionId -> {
            currentState.copy(questionId = effect.questionId)
        }

        is QuestionFactoryStore.Effect.SetTitle -> {
            currentState.copy(title = effect.title)
        }

        is QuestionFactoryStore.Effect.ToggleMultipleAnswerChoice -> currentState.copy(
            isMultipleAnswerChoice = effect.isMultipleAnswerChoice
        )
    }

    override fun handleEvent(
        currentState: QuestionFactoryStore.State,
        intent: QuestionFactoryStore.Event,
    ): Flow<QuestionFactoryStore.Effect> {
        return when (intent) {
            QuestionFactoryStore.Event.Close -> flow {
                sendSideEffect(QuestionFactoryStore.SideEffect.Close)
            }

            is QuestionFactoryStore.Event.Save -> flow {
                val hasEmptyField = currentState.answerChoice.any { it.text.isBlank() }
                        || currentState.question.isBlank()
                if (hasEmptyField) {
                    emit(
                        QuestionFactoryStore.Effect.VisibleBottomSheet(
                            testId = currentState.testId,
                            message = appResource.getString(R.string.toast_fill_all_fields)
                        )
                    )
                    return@flow
                }

                val hasRightAnswer = currentState.answerChoice.any { it.isRightAnswer }
                if (!hasRightAnswer) {
                    emit(
                        QuestionFactoryStore.Effect.VisibleBottomSheet(
                            testId = currentState.testId,
                            message = appResource.getString(R.string.toast_need_right_answer)
                        )
                    )
                    return@flow
                }

                val questionId = withIO {
                    if (currentState.questionId == -1) {
                        val createdId = questionRepository.createQuestion(
                            question = currentState.question,
                            testId = currentState.testId,
                        )
                        val answerChoiceData = currentState.answerChoice.map {
                            val newItem = it.copy(
                                id = 0,
                                questionId = createdId
                            )
                            newItem
                        }
                        answerChoiceRepository.createAnswerChoiceList(answerChoiceData)
                        createdId
                    } else {
                        answerChoiceRepository.deleteAnswerChoicesByQuestionId(currentState.questionId)
                        questionRepository.updateQuestion(
                            questionId = currentState.questionId,
                            question = currentState.question,
                        )
                        val answerChoiceData = currentState.answerChoice.map {
                            val newItem = it.copy(
                                id = 0,
                                questionId = currentState.questionId
                            )
                            newItem
                        }
                        answerChoiceRepository.createAnswerChoiceList(answerChoiceData)
                        currentState.questionId
                    }
                }
                sendSideEffect(QuestionFactoryStore.SideEffect.OpenTestManager)
            }

            is QuestionFactoryStore.Event.ShowMessageBottomSheet -> flow {
                emit(QuestionFactoryStore.Effect.VisibleBottomSheet(testId = -1))
                emit(
                    QuestionFactoryStore.Effect.VisibleBottomSheet(
                        testId = currentState.testId,
                        message = intent.message
                    )
                )
            }

            is QuestionFactoryStore.Event.Back -> flow {
                sendSideEffect(QuestionFactoryStore.SideEffect.Close)
            }

            is QuestionFactoryStore.Event.SelectRightAnswer -> flow {
                val updatedAnswers = currentState.answerChoice.map { answer ->
                    if (answer.id == intent.answerId) {
                        answer.copy(isRightAnswer = !answer.isRightAnswer)
                    } else {
                        answer
                    }
                }
                emit(QuestionFactoryStore.Effect.UpdateAnswerList(answers = updatedAnswers))
                val isMultipleAnswerChoice = updatedAnswers.count { it.isRightAnswer } > 1
                emit(QuestionFactoryStore.Effect.ToggleMultipleAnswerChoice(isMultipleAnswerChoice = isMultipleAnswerChoice))
            }

            is QuestionFactoryStore.Event.UpdateQuestion -> flow {
                emit(QuestionFactoryStore.Effect.UpdateText(question = intent.question))
            }

            is QuestionFactoryStore.Event.AddAnswer -> flow {
                val countAnswersChoice = currentState.answerChoice.size;
                val lastIndex = currentState.answerChoice.lastOrNull()?.id ?: 0
                if (countAnswersChoice < 8) {
                    val newAnswer = AnswerChoiceData(
                        id = lastIndex + 1,
                        text = "",
                        isRightAnswer = false,
                        questionId = 0
                    )
                    val updatedAnswers = currentState.answerChoice + newAnswer
                    emit(QuestionFactoryStore.Effect.UpdateAnswerList(updatedAnswers))
                } else {
                    emit(
                        QuestionFactoryStore.Effect.VisibleBottomSheet(
                            testId = currentState.testId,
                            message = appResource.getString(R.string.toast_max_answer_choice)
                        )
                    )
                }
            }

            is QuestionFactoryStore.Event.RemoveAnswer -> flow {
                if (currentState.answerChoice.size <= 2) {
                    emit(
                        QuestionFactoryStore.Effect.VisibleBottomSheet(
                            testId = currentState.testId,
                            message = appResource.getString(R.string.toast_min_answers_choice)
                        )
                    )
                    return@flow
                }
                val answer = currentState.answerChoice.find { it.id == intent.index } ?: return@flow
                val newList = currentState.answerChoice - answer
                emit(QuestionFactoryStore.Effect.UpdateAnswerList(newList))
            }

            is QuestionFactoryStore.Event.ToggleCorrect -> flow {
                val updatedAnswers = currentState.answerChoice.map { item ->
                    item.copy(isRightAnswer = item.id == intent.id)
                }
                emit(QuestionFactoryStore.Effect.UpdateAnswerList(updatedAnswers))
            }

            is QuestionFactoryStore.Event.UpdateAnswer -> flow {
                val updatedAnswers = currentState.answerChoice.map {
                    if (it.id == intent.id) it.copy(text = intent.answer) else it
                }
                emit(QuestionFactoryStore.Effect.UpdateAnswerList(updatedAnswers))
            }

            is QuestionFactoryStore.Event.CloseBottomSheet -> flow {
                emit(
                    QuestionFactoryStore.Effect.VisibleBottomSheet(
                        testId = -1
                    )
                )
            }

            is QuestionFactoryStore.Event.LoadQuestion -> flow {
                val question = withIO {
                    questionRepository.getQuestionWithAnswersByQuestionId(intent.questionId)
                }

                if (question == null) {
                    // Вопрос не найден (возможно, был удалён), возвращаемся назад
                    sendSideEffect(QuestionFactoryStore.SideEffect.Close)
                    return@flow
                }
                
                emit(QuestionFactoryStore.Effect.UpdateText(question.question.questionText))
                emit(QuestionFactoryStore.Effect.UpdateAnswerList(question.answers))
                emit(QuestionFactoryStore.Effect.LoadScreen(question.question.testId))
                emit(QuestionFactoryStore.Effect.UpdateQuestionId(question.question.id))
                emit(QuestionFactoryStore.Effect.ToggleMultipleAnswerChoice(question.question.isMultipleAnswerChoice))
                if (question.question.id != -1) {
                    emit(QuestionFactoryStore.Effect.SetTitle(title = appResource.getString(R.string.title_question_factory_edit_question)))
                }
            }

            is QuestionFactoryStore.Event.CreateNewQuestion -> flow {
                emit(QuestionFactoryStore.Effect.UpdateQuestionId(-1))
                emit(QuestionFactoryStore.Effect.UpdateText(""))
                emit(
                    QuestionFactoryStore.Effect.UpdateAnswerList(
                        listOf(
                            AnswerChoiceData(
                                id = 0,
                                text = "",
                                isRightAnswer = false,
                                questionId = 0
                            ),
                            AnswerChoiceData(
                                id = 1,
                                text = "",
                                isRightAnswer = false,
                                questionId = 0
                            )
                        )
                    )
                )
                emit(
                    QuestionFactoryStore.Effect.SetTitle(
                        title = appResource.getString(R.string.title_question_factory_create_question)
                    )
                )
            }
        }
    }
}