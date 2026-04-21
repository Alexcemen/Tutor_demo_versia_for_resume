package ru.project.tutor.domain.repository

interface AppSharedPreferences {
    var firstLaunchDate: String
    var allCountTestCreated: Int
    var allCountErrors: Int
    var allCountTestsCompleted: Int
    var allCountTime: Long
    var isDontShowTooErrorsDialogAgain: Boolean
    var notificationsEnabled: Boolean
    var notificationMessageIndex: Int
    var isOnboardingShown: Boolean
    var isTelegramBannerShown: Boolean
    var isAdExplanationBannerShown: Boolean
    var selectedLanguage: String
}