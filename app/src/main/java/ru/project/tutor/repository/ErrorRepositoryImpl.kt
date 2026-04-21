package ru.project.tutor.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.project.tutor.database.dao.ErrorDao
import ru.project.tutor.database.entity.ErrorEntity
import ru.project.tutor.domain.models.error.ErrorData
import ru.project.tutor.domain.repository.ErrorRepository

class ErrorRepositoryImpl(
    private val errorDao: ErrorDao,
) : ErrorRepository {

    override suspend fun createError(testId: Int, questionId: Int) = errorDao.insert(
        ErrorEntity(
            testId = testId,
            questionId = questionId
        )
    ).toInt()

    override fun getErrorsByTestId(testId: Int): Flow<List<ErrorData>> =
        errorDao.getErrorsByTestId(testId).map { errors ->
            errors.map { errorEntity ->
                ErrorData(
                    testId = errorEntity.testId,
                    questionId = errorEntity.questionId
                )
            }
        }

    override suspend fun deleteErrorByQuestionId(questionId: Int) {
        errorDao.deleteErrorsByQuestionId(questionId = questionId)
    }
}