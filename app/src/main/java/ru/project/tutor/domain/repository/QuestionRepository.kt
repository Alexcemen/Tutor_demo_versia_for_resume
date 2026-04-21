package ru.project.tutor.domain.repository

import ru.project.tutor.domain.models.question_with_answers.QuestionWithAnswersData

interface QuestionRepository {
    suspend fun createQuestion(
        question: String,
        testId: Int,
    ): Int

    suspend fun getQuestionsWithAnswersByTestId(testId: Int): List<QuestionWithAnswersData>

    suspend fun getQuestionWithAnswersByQuestionId(questionId: Int): QuestionWithAnswersData?

    suspend fun deleteQuestion(questionId: Int, testId: Int)

    suspend fun updateQuestion(questionId: Int, question: String)

    suspend fun getMaxPosition(testId: Int): Int

    suspend fun updatePosition(testId: Int, deletedPosition: Int)

    suspend fun getPositionByQuestionId(questionId: Int): Int
}