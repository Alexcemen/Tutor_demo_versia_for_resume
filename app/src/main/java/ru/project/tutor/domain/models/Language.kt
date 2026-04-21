package ru.project.tutor.domain.models

import java.util.Locale

data class Language(
    val code: String,
) {
    companion object {
        const val CODE_SYSTEM = "system"

        val supportedLanguages = listOf(
            Language(CODE_SYSTEM),
            Language("az"),
            Language("be"),
            Language("kk"),
            Language("ru"),
            Language("tg"),
            Language("uk"),
            Language("uz"),
            Language("en"),
        )

        val supportedLocaleCodes = supportedLanguages.map { it.code }.filter { it != CODE_SYSTEM }

        /**
         * Возвращает ресурсный ID для отображения названия языка.
         * Формат: R.string.language_name_{code}
         */
        fun Language.nameResId(): Int {
            return when (code) {
                CODE_SYSTEM -> ru.project.tutor.R.string.language_name_system
                "az" -> ru.project.tutor.R.string.language_name_az
                "be" -> ru.project.tutor.R.string.language_name_be
                "kk" -> ru.project.tutor.R.string.language_name_kk
                "ru" -> ru.project.tutor.R.string.language_name_ru
                "tg" -> ru.project.tutor.R.string.language_name_tg
                "uk" -> ru.project.tutor.R.string.language_name_uk
                "uz" -> ru.project.tutor.R.string.language_name_uz
                "en" -> ru.project.tutor.R.string.language_name_en
                else -> ru.project.tutor.R.string.language_name_en
            }
        }

        /**
         * Если системный язык не из списка поддерживаемых, возвращаем "en".
         */
        fun getDefaultLanguageCode(): String {
            val systemLocale = Locale.getDefault()
            val systemLanguageCode = systemLocale.language
            return if (systemLanguageCode in supportedLocaleCodes) {
                systemLanguageCode
            } else {
                "en"
            }
        }
    }
}
