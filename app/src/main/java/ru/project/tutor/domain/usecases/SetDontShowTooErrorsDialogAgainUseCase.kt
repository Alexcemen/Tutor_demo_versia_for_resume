package ru.project.tutor.domain.usecases

import ru.project.tutor.domain.repository.AppSharedPreferences

class SetDontShowTooErrorsDialogAgainUseCase(
    private val preferences: AppSharedPreferences,
) {
    operator fun invoke(
        isDontShowAgainCheckbox: Boolean,
    ) {
        preferences.isDontShowTooErrorsDialogAgain = isDontShowAgainCheckbox
    }
}