package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.TestRepository
import ru.project.tutor.utils.withIO

class SetDateLastTakeTestUseCase(
    private val testRepository: TestRepository,
) {
    suspend operator fun invoke(
        testId: Int,
        dateLastTakeTest: Long,
    ) = withIO {
        testRepository.setDateLastTakeTest(
            testId = testId,
            dateLastTakeTest = dateLastTakeTest
        )
    }
}