package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.AppSharedPreferences

class AllCountErrorsIncrementUseCase(
    private val appSharedPreferences: AppSharedPreferences,
) {
    operator fun invoke() {
        appSharedPreferences.allCountErrors += 1
    }
}