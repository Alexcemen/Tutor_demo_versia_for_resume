package ru.project.tutor.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.project.tutor.database.dao.FavoriteDao
import ru.project.tutor.database.entity.FavoriteEntity
import ru.project.tutor.domain.models.favorite.FavoriteData
import ru.project.tutor.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl(
    private val favoriteDao: FavoriteDao,
) : FavoriteRepository {
    override suspend fun createFavorite(testId: Int, questionId: Int) = favoriteDao.insert(
        FavoriteEntity(
            testId = testId,
            questionId = questionId
        )
    ).toInt()

    override fun getFavoritesByTestId(testId: Int): Flow<List<FavoriteData>> =
        favoriteDao.getFavoritesByTestId(testId).map { favorites ->
            favorites.map { favoriteEntity ->
                FavoriteData(
                    testId = favoriteEntity.testId,
                    questionId = favoriteEntity.questionId
                )
            }
        }

    override suspend fun deleteFavoriteByQuestionId(questionId: Int) {
        favoriteDao.deleteFavoritesByQuestionId(questionId)
    }
}