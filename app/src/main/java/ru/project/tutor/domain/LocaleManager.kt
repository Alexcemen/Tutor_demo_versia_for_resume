package ru.project.tutor.domain

import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import ru.project.tutor.domain.models.Language
import ru.project.tutor.domain.models.Language.Companion.nameResId
import ru.project.tutor.domain.repository.AppSharedPreferences
import ru.project.tutor.domain.repository.ErrorLogger
import timber.log.Timber
import java.util.Locale

class LocaleManager(
    private val appSharedPreferences: AppSharedPreferences,
    private val errorLogger: ErrorLogger,
) {
    private val log = Timber.tag(LocaleManager::class.java.name)

    /**
     * Поддерживается ли выбор языка в приложении.
     * Выбор языка доступен только на API 33+ (Android 13+).
     */
    fun isLanguageSelectionSupported(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    /**
     * Применяет локаль, сохранённую в настройках.
     * Если не выбрана (пустая строка или "system") — используется системная.
     * Если системный язык не из поддерживаемых — английский.
     * На API < 33 всегда используется системный язык.
     */
    fun applyLocale(context: Context) {
        try {
            if (!isLanguageSelectionSupported()) {
                log.i("language selection not supported on API < 33, using system locale")
                return
            }
            val savedLanguage = getSavedLanguage()
            log.i("applying locale: ${savedLanguage.code}")
            setAppLocale(context, savedLanguage.code)
        } catch (e: Exception) {
            log.e(e, "failed to apply locale")
            errorLogger.logException(e)
        }
    }

    /**
     * Переключает язык приложения.
     * На API < 33 не делает ничего, т.к. выбор языка не поддерживается.
     */
    fun setLanguage(languageCode: String, context: Context) {
        if (!isLanguageSelectionSupported()) {
            log.w("language selection not supported on API < 33, ignoring")
            return
        }
        try {
            log.i("setting language: $languageCode")
            appSharedPreferences.selectedLanguage = languageCode
            setAppLocale(context, languageCode)
        } catch (e: Exception) {
            log.e(e, "failed to set language: $languageCode")
            errorLogger.logException(e)
        }
    }

    /**
     * Возвращает текущий выбранный язык.
     * На API < 33 всегда возвращает системный язык.
     */
    fun getSavedLanguage(): Language {
        if (!isLanguageSelectionSupported()) {
            return Language(Language.CODE_SYSTEM)
        }
        val savedCode = appSharedPreferences.selectedLanguage
        if (savedCode.isBlank() || savedCode == Language.CODE_SYSTEM) {
            return Language(Language.CODE_SYSTEM)
        }
        return Language.supportedLanguages.find { it.code == savedCode }
            ?: Language(Language.CODE_SYSTEM)
    }

    /**
     * Возвращает ресурсный ID названия языка для отображения в UI.
     */
    fun getLanguageNameResId(language: Language): Int = language.nameResId()

    private fun setAppLocale(context: Context, languageCode: String) {
        if (languageCode.isBlank() || languageCode == Language.CODE_SYSTEM) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                LocaleManagerApi33.setApplicationLocales(context, LocaleList.getEmptyLocaleList())
            } else {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
            }
        } else {
            val locale = Locale.forLanguageTag(languageCode)
            Locale.setDefault(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                LocaleManagerApi33.setApplicationLocales(context, LocaleList(locale))
            } else {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(locale))
            }
        }
    }
}
