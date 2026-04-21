package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.TestRepository
import ru.project.tutor.utils.withIO

class GetDateCreateTestUseCase(
    private val testRepository: TestRepository,
) {
    suspend operator fun invoke(
        testId: Int,
    ): Long = withIO {
        testRepository.getDateCreateTest(
            testId = testId
        )
    }
}