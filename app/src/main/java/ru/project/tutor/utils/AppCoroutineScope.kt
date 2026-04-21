package ru.project.tutor.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class AppCoroutineScope : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()
}