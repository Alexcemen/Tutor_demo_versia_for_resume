package ru.project.tutor.domain.repository

import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData

interface AnswerChoiceRepository {
    suspend fun createAnswerChoiceList(
        answers: List<AnswerChoiceData>
    ): List<Int>

    suspend fun getAnswerChoiceList(questionId: Int): List<AnswerChoiceData>

    suspend fun deleteAnswerChoicesByQuestionId(questionId: Int)
}