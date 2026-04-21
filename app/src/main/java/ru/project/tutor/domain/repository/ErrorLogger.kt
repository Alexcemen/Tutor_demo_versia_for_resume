package ru.project.tutor.domain.repository

interface ErrorLogger {
    fun logMessage(msg: String)
    fun logException(t: Throwable)
}