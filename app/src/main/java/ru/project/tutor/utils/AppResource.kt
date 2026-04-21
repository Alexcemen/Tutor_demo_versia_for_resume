package ru.project.tutor.utils

import android.content.Context
import androidx.annotation.StringRes

class AppResourceImpl(private val context: Context) : AppResource {
    override fun getString(id: Int, vararg formatArgs: Any) = context.getString(id, *formatArgs)
}

interface AppResource {
    fun getString(@StringRes id: Int, vararg formatArgs: Any): String
}