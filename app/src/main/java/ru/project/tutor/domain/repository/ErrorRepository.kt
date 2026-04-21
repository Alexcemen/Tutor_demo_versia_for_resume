package ru.project.tutor.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.project.tutor.domain.models.error.ErrorData

interface ErrorRepository {
    suspend fun createError(
        testId: Int,
        questionId: Int,
    ): Int

    fun getErrorsByTestId(testId: Int): Flow<List<ErrorData>>

    suspend fun deleteErrorByQuestionId(questionId: Int)
}