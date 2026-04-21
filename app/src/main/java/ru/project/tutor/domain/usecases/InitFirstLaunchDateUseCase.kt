package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.AppSharedPreferences
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InitFirstLaunchDateUseCase(
    private val appSharedPreferences: AppSharedPreferences,
) {
    operator fun invoke() {
        if (appSharedPreferences.firstLaunchDate.isEmpty()) {
            val dateTimePattern = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            appSharedPreferences.firstLaunchDate = LocalDate.now().format(dateTimePattern)
        }
    }
}