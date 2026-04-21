package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.AppSharedPreferences

class GetDontShowTooErrorsDialogAgainUseCase(
    private val preferences: AppSharedPreferences,
) {
    operator fun invoke(): Boolean =
        preferences.isDontShowTooErrorsDialogAgain
}