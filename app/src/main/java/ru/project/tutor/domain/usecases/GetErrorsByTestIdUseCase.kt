package ru.project.tutor.domain.usecases

import kotlinx.coroutines.flow.first
import ru.project.tutor.domain.models.error.ErrorData
import ru.project.tutor.domain.repository.ErrorRepository
import ru.project.tutor.utils.withIO

class GetErrorsByTestIdUseCase(
    private val errorRepository: ErrorRepository,
) {
    suspend operator fun invoke(
        testId: Int,
    ): List<ErrorData> = withIO {
        errorRepository.getErrorsByTestId(testId).first()
    }
}