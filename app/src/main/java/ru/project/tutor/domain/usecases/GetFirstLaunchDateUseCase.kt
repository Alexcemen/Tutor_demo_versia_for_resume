package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.AppSharedPreferences

class GetFirstLaunchDateUseCase(
    private val appSharedPreferences: AppSharedPreferences,
) {
    operator fun invoke(): String {
        return appSharedPreferences.firstLaunchDate
    }
}