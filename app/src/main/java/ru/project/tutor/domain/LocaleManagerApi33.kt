package ru.project.tutor.domain

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi

/**
 * Helper для работы с LocaleManager на API 33+.
 * Вынесен в отдель класс, чтобы избежать NoClassDefFoundError на API < 33.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
object LocaleManagerApi33 {
    fun setApplicationLocales(context: Context, localeList: LocaleList) {
        context.getSystemService(LocaleManager::class.java).applicationLocales = localeList
    }
}
