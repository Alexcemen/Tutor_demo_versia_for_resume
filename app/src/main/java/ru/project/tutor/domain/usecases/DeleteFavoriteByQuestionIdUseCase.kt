package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.FavoriteRepository
import ru.project.tutor.utils.withIO

class DeleteFavoriteByQuestionIdUseCase(
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(
        questionId: Int,
    ) = withIO {
        favoriteRepository.deleteFavoriteByQuestionId(questionId)
    }
}