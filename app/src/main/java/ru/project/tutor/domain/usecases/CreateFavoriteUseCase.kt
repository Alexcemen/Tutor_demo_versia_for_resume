package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.FavoriteRepository
import ru.project.tutor.utils.withIO

class CreateFavoriteUseCase(
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(
        testId: Int,
        questionId: Int,
    ) = withIO {
        favoriteRepository.createFavorite(
            testId = testId,
            questionId = questionId
        )
    }
}