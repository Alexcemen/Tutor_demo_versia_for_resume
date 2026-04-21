package ru.project.tutor.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <T> withIO(
    crossinline block: suspend CoroutineScope.() -> T
) = withContext(Dispatchers.IO) {
    block.invoke(this)
}