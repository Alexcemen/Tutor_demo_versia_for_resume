package ru.project.tutor.repository

import android.content.Context
import android.content.SharedPreferences
import ru.project.tutor.common_ui.shared.PrefsDelegate
import ru.project.tutor.domain.repository.AppSharedPreferences
import ru.project.tutor.domain.repository.ErrorLogger
import timber.log.Timber

class AppSharedPreferencesImpl(
    private val errorLogger: ErrorLogger,
    context: Context,
) : AppSharedPreferences {
    private val log = Timber.tag(AppSharedPreferences::class.java.name)

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    private val errorCatch: (String) -> Unit = {
        errorLogger.logMessage(it)
    }

    override var firstLaunchDate: String by PrefsDelegate(
        mPrefs,
        FIRST_LAUNCH_DATE,
        "",
        errorCatch
    )

    override var allCountTestCreated: Int by PrefsDelegate(
        mPrefs,
        ALL_COUNT_TEST_CREATED,
        0,
        errorCatch
    )

    override var allCountErrors: Int by PrefsDelegate(
        mPrefs,
        ALL_COUNT_ERRORS,
        0,
        errorCatch
    )

    override var allCountTestsCompleted: Int by PrefsDelegate(
        mPrefs,
        ALL_COUNT_TESTS_COMPLETED,
        0,
        errorCatch
    )

    override var allCountTime: Long by PrefsDelegate(
        mPrefs,
        ALL_COUNT_TIME,
        0,
        errorCatch
    )

    override var isDontShowTooErrorsDialogAgain: Boolean by PrefsDelegate(
        mPrefs,
        IS_DONT_SHOW_TOO_ERRORS_DIALOG_AGAIN,
        false,
        errorCatch
    )

    override var notificationsEnabled: Boolean by PrefsDelegate(
        mPrefs,
        NOTIFICATIONS_ENABLED,
        true,
        errorCatch
    )

    override var notificationMessageIndex: Int by PrefsDelegate(
        mPrefs,
        NOTIFICATION_MESSAGE_INDEX,
        0,
        errorCatch
    )

    override var isOnboardingShown: Boolean by PrefsDelegate(
        mPrefs,
        IS_ONBOARDING_SHOWN,
        false,
        errorCatch
    )

    override var isTelegramBannerShown: Boolean by PrefsDelegate(
        mPrefs,
        IS_TELEGRAM_BANNER_SHOWN,
        false,
        errorCatch
    )

    override var isAdExplanationBannerShown: Boolean by PrefsDelegate(
        mPrefs,
        IS_AD_EXPLANATION_BANNER_SHOWN,
        false,
        errorCatch
    )

    override var selectedLanguage: String by PrefsDelegate(
        mPrefs,
        SELECTED_LANGUAGE,
        "",
        errorCatch
    )

    companion object {
        private const val SHARED_PREFERENCES_NAME = "tutor_shared_pref"

        private const val FIRST_LAUNCH_DATE = "first_launch_date"
        private const val ALL_COUNT_TEST_CREATED = "all_count_test_created"
        private const val ALL_COUNT_ERRORS = "all_count_errors"
        private const val ALL_COUNT_TESTS_COMPLETED = "all_count_tests_completed"
        private const val ALL_COUNT_TIME = "all_count_time"
        private const val IS_DONT_SHOW_TOO_ERRORS_DIALOG_AGAIN =
            "is_dont_show_too_errors_dialog_again"
        private const val NOTIFICATIONS_ENABLED = "notifications_enabled"
        private const val NOTIFICATION_MESSAGE_INDEX = "notification_message_index"
        private const val IS_ONBOARDING_SHOWN = "is_onboarding_shown"
        private const val IS_TELEGRAM_BANNER_SHOWN = "is_telegram_banner_shown"
        private const val IS_AD_EXPLANATION_BANNER_SHOWN = "is_ad_explanation_banner_shown"
        private const val SELECTED_LANGUAGE = "selected_language"
    }
}