package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.AppSharedPreferences

class AddAllCountTimeUseCase(
    private val appSharedPreferences: AppSharedPreferences,
) {
    operator fun invoke(testingStartTime: Long) {
        appSharedPreferences.allCountTime += (System.currentTimeMillis() - testingStartTime)
    }
}