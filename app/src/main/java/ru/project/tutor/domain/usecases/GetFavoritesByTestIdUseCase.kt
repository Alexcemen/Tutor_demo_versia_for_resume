package ru.project.tutor.domain.usecases

import kotlinx.coroutines.flow.first
import ru.project.tutor.domain.models.favorite.FavoriteData
import ru.project.tutor.domain.repository.FavoriteRepository
import ru.project.tutor.utils.withIO

class GetFavoritesByTestIdUseCase(
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(
        testId: Int,
    ): List<FavoriteData> = withIO {
        favoriteRepository.getFavoritesByTestId(
            testId = testId
        ).first()
    }
}