package ru.project.tutor.domain.usecases

class CompletedTestUseCase(
    private val allCountTestsCompletedIncrementUseCase: AllCountTestsCompletedIncrementUseCase,
    private val setDateLastTakeTestUseCase: SetDateLastTakeTestUseCase,
    private val addAllCountTimeUseCase: AddAllCountTimeUseCase,
) {
    suspend operator fun invoke(
        testId: Int,
        testingStartTime: Long,
    ) {
        allCountTestsCompletedIncrementUseCase()
        setDateLastTakeTestUseCase(
            testId = testId,
            dateLastTakeTest = System.currentTimeMillis()
        )
        addAllCountTimeUseCase(testingStartTime = testingStartTime)
    }
}