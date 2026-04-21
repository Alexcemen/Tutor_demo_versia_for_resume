package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.AppSharedPreferences

class GetAllCountTestsCompletedUseCase(
    private val appSharedPreferences: AppSharedPreferences,
) {
    operator fun invoke(): Int {
        return appSharedPreferences.allCountTestsCompleted
    }
}