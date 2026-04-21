package ru.project.tutor.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

interface ReviewStarterSharedFlow {
    val subscriber: SharedFlow<Unit>
    suspend fun start()
}

class ReviewStarterSharedFlowImpl : ReviewStarterSharedFlow {
    private val _subscriber: MutableSharedFlow<Unit> = MutableSharedFlow()
    override val subscriber: SharedFlow<Unit> = _subscriber

    override suspend fun start() {
        _subscriber.emit(Unit)
    }
}
