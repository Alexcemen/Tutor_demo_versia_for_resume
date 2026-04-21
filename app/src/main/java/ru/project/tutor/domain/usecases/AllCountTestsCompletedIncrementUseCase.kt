package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.AppSharedPreferences

class AllCountTestsCompletedIncrementUseCase(
    private val appSharedPreferences: AppSharedPreferences,
) {
    operator fun invoke() {
        appSharedPreferences.allCountTestsCompleted += 1
    }
}