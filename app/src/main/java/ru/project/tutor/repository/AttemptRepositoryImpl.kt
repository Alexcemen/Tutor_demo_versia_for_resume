package ru.project.tutor.repository

import ru.project.tutor.database.dao.AttemptDao
import ru.project.tutor.database.entity.AttemptEntity
import ru.project.tutor.domain.repository.AttemptRepository

class AttemptRepositoryImpl(
    private val attemptDao: AttemptDao,
) : AttemptRepository {

    override suspend fun createAttempt(testId: Int, questions: String) = attemptDao.insert(
        AttemptEntity(
            testId = testId,
            questions = questions
        )
    ).toInt()

    override suspend fun getQuestions(attemptId: Int): String {
        return attemptDao.getQuestions(attemptId)
    }
}