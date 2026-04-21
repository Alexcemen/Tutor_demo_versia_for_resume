package ru.project.tutor.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.project.tutor.domain.models.test.TestData
import ru.project.tutor.domain.models.test.TestWithCountQuestions
import ru.project.tutor.domain.models.test.TestWithQuestionsData

interface TestRepository {
    suspend fun createTest(
        title: String,
    ): Int

    suspend fun getTest(id: Int): TestData

    fun getAllTest(): Flow<List<TestData>>

    fun getTestsWithCounts(): Flow<List<TestWithCountQuestions>>

    suspend fun deleteTest(testId: Int)

    suspend fun getTestWithQuestions(testId: Int): TestWithQuestionsData

    suspend fun getDateCreateTest(testId: Int): Long

    suspend fun getDateLastTakeTest(testId: Int): Long

    suspend fun setDateLastTakeTest(testId: Int, dateLastTakeTest: Long)
}