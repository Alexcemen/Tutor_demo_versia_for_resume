package ru.project.tutor.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.project.tutor.database.dao.TestDao
import ru.project.tutor.database.entity.TestEntity
import ru.project.tutor.database.models.TestWithCountsEntity
import ru.project.tutor.domain.models.test.TestData
import ru.project.tutor.domain.models.test.TestWithCountQuestions
import ru.project.tutor.domain.models.test.TestWithQuestionsData
import ru.project.tutor.domain.repository.AppSharedPreferences
import ru.project.tutor.domain.repository.QuestionRepository
import ru.project.tutor.domain.repository.TestRepository
import ru.project.tutor.utils.TestDesign

class TestRepositoryImpl(
    private val testDao: TestDao,
    private val questionRepository: QuestionRepository,
    private val appSharedPreferences: AppSharedPreferences,
) : TestRepository {
    override suspend fun createTest(title: String): Int {
        val toInt = testDao.insert(
            TestEntity(
                title = title,
                colorId = TestDesign.ColorValue.generateColorValue(),
                imageId = TestDesign.ImageValue.generateImageValue(),
                dateCreation = System.currentTimeMillis(),
                dateLastTake = System.currentTimeMillis()
            )
        ).toInt()
        appSharedPreferences.allCountTestCreated += 1
        return toInt
    }

    override suspend fun getTest(id: Int): TestData {
        val testEntity = testDao.get(id)
        return testEntity.convertToDomain()
    }

    override fun getAllTest(): Flow<List<TestData>> =
        testDao.getAll().map { allTests ->
            allTests.map { entity ->
                entity.convertToDomain()
            }
        }

    override fun getTestsWithCounts(): Flow<List<TestWithCountQuestions>> {
        return testDao.getTestsWithCounts().map { list ->
            list.map { entity ->
                entity.toDomain()
            }
        }
    }


    private fun TestEntity.convertToDomain() = TestData(
        id = id,
        title = title,
        colorId = colorId,
        imageId = imageId,
        dateLastTake = dateLastTake,
        dateCreation = dateCreation
    )

    private fun TestWithCountsEntity.toDomain() = TestWithCountQuestions(
        testData = test.convertToDomain(),
        normalQuestionsCount = questionsCount,
        favoriteQuestionsCount = favoritesCount,
        errorQuestionsCount = errorsCount
    )


    override suspend fun deleteTest(testId: Int) {
        testDao.deleteTest(id = testId)
    }

    override suspend fun getTestWithQuestions(testId: Int): TestWithQuestionsData {
        val testEntity = testDao.get(testId)
        val testData = testEntity.convertToDomain()
        val questions = questionRepository.getQuestionsWithAnswersByTestId(testId)
        return TestWithQuestionsData(
            testData = testData,
            questions = questions
        )
    }

    override suspend fun getDateCreateTest(testId: Int): Long {
        return testDao.getDateCreation(id = testId)
    }

    override suspend fun getDateLastTakeTest(testId: Int): Long {
        return testDao.getDateLastTake(id = testId)
    }

    override suspend fun setDateLastTakeTest(testId: Int, dateLastTakeTest: Long) {
        testDao.setDateLastTake(
            id = testId,
            dateLastTake = dateLastTakeTest
        )
    }
}