package ru.project.tutor.common_ui.shared

import android.content.SharedPreferences
import kotlin.reflect.KProperty

class PrefsDelegate<T>(
    private val prefs: SharedPreferences,
    private val key: String,
    private val defaultValue: T,
    private val errorCatch: (String) -> Unit,
) {
    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        requireNotNull(defaultValue)
        return try {
            val data = when (defaultValue) {
                is Boolean -> prefs.getBoolean(key, defaultValue) as? T
                is String -> prefs.getString(key, defaultValue) as? T
                is Int -> prefs.getInt(key, defaultValue) as? T
                is Long -> prefs.getLong(key, defaultValue) as? T
                is Float -> prefs.getFloat(key, defaultValue) as? T
                else -> throw IllegalArgumentException("Type is not supported")
            }
            data ?: defaultValue
        } catch (e: Exception) {
            errorCatch("shared preferences get value failed with msg ${e.message}")
            defaultValue
        }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        val editor = prefs.edit()
        if (value == null) {
            editor.remove(key)
            editor.apply()
            return
        }
        try {
            when (value) {
                is Boolean -> editor.putBoolean(key, value)
                is String -> editor.putString(key, value)
                is Int -> editor.putInt(key, value)
                is Long -> editor.putLong(key, value)
                is Float -> editor.putFloat(key, value)
                else -> throw IllegalArgumentException("Type is not supported")
            }

            editor.apply()
        } catch (e: Exception) {
            errorCatch("shared preferences set value failed with msg ${e.message}")
        }
    }
}
