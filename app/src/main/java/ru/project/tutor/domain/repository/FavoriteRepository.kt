package ru.project.tutor.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.project.tutor.domain.models.favorite.FavoriteData

interface FavoriteRepository {
    suspend fun createFavorite(
        testId: Int,
        questionId: Int,
    ): Int

    fun getFavoritesByTestId(testId: Int): Flow<List<FavoriteData>>

    suspend fun deleteFavoriteByQuestionId(questionId: Int)
}