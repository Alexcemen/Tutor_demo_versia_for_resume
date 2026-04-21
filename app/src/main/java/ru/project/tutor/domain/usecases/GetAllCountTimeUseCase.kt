package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.AppSharedPreferences

class GetAllCountTimeUseCase(
    private val appSharedPreferences: AppSharedPreferences,
) {
    operator fun invoke(): Long {
        return appSharedPreferences.allCountTime
    }
}