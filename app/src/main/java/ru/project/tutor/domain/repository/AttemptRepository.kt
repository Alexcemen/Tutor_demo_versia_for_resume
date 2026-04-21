package ru.project.tutor.domain.repository


interface AttemptRepository {
    suspend fun createAttempt(
        testId: Int,
        questions: String,
    ): Int

    suspend fun getQuestions(attemptId: Int): String
}