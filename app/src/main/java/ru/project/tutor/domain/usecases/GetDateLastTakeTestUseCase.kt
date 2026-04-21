package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.TestRepository
import ru.project.tutor.utils.withIO

class GetDateLastTakeTestUseCase(
    private val testRepository: TestRepository,
) {
    suspend operator fun invoke(
        testId: Int,
    ): Long = withIO {
        testRepository.getDateLastTakeTest(
            testId = testId
        )
    }
}