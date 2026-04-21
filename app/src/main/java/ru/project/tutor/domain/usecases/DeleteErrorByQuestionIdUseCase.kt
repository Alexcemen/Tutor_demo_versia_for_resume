package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.ErrorRepository
import ru.project.tutor.utils.withIO

class DeleteErrorByQuestionIdUseCase(
    private val errorRepository: ErrorRepository,
) {
    suspend operator fun invoke(
        questionId: Int,
    ) = withIO {
        errorRepository.deleteErrorByQuestionId(questionId = questionId)
    }
}