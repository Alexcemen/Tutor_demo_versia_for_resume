package ru.project.tutor.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.project.tutor.domain.usecases.SaveTestSharedDataUseCase
import ru.project.tutor.utils.createNewTestFromUri
import timber.log.Timber

class MainActivityViewModel(
    private val saveTestSharedDataUseCase: SaveTestSharedDataUseCase,
    private val context: Context,
) : ViewModel() {
    private val _sideEffectTestId = MutableSharedFlow<Int>()
    val sideEffectTestId: SharedFlow<Int> = _sideEffectTestId.asSharedFlow()

    fun processSaveSharedTest(uri: Uri) {
        viewModelScope.launch {
            val testId =
                context.createNewTestFromUri(saveTestSharedDataUseCase, uri) ?: return@launch
            Timber.tag(MainActivityViewModel::class.java.name)
                .i("create test from handle new intent")
            _sideEffectTestId.emit(testId)
        }
    }
}