package ru.project.tutor.utils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.annotation.StringRes
import ru.project.tutor.R
import ru.project.tutor.analytics.AppAnalytics
import ru.project.tutor.domain.usecases.SaveTestSharedDataUseCase
import java.io.FileNotFoundException

fun Context.showError(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes text: Int) {
    Toast.makeText(this, getString(text), Toast.LENGTH_SHORT).show()
}

suspend fun Context.createNewTestFromUri(
    saveTestSharedDataUseCase: SaveTestSharedDataUseCase,
    uri: Uri,
): Int? {
    return try {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            saveTestSharedDataUseCase.invoke(jsonString)
        } ?: run {
            showToast(R.string.handle_file_json_error_read)
            AppAnalytics.errorCreateTestFromFile()
            null
        }
    } catch (e: FileNotFoundException) {
        AppErrorLogger.logException(e)
        showToast(R.string.handle_file_json_error_not_found)
        AppAnalytics.errorCreateTestFromFile()
        null
    }
}
