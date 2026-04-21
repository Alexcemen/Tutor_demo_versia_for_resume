package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.ErrorRepository
import ru.project.tutor.utils.withIO

class CreateErrorUseCase(
    private val errorRepository: ErrorRepository,
) {
    suspend operator fun invoke(
        testId: Int,
        questionId: Int,
    ) = withIO {
        errorRepository.createError(
            testId = testId,
            questionId = questionId
        )
    }
}