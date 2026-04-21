package ru.project.tutor.utils

import ru.project.tutor.domain.repository.ErrorLogger
import timber.log.Timber

object AppErrorLogger : ErrorLogger {
    override fun logMessage(msg: String) {
        Timber.tag(AppErrorLogger::class.java.name).e(RuntimeException(msg))
    }

    override fun logException(t: Throwable) {
        Timber.tag(AppErrorLogger::class.java.name).e(t)
    }
}
