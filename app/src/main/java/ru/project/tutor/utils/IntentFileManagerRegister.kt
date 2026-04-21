package ru.project.tutor.utils

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class IntentFileManagerRegister {
    private val fileManagerCallback: ActivityResultCallback<Uri?> =
        ActivityResultCallback { result ->
            result ?: return@ActivityResultCallback
            onResult?.invoke(result)
            onResult = null
        }
    private var launcher: ActivityResultLauncher<String>? = null
    private var onResult: ((Uri) -> Unit)? = null

    fun init(
        activity: ComponentActivity,
    ) {
        launcher = activity.registerForActivityResult(
            ActivityResultContracts.GetContent(), fileManagerCallback
        )
    }

    fun launch(options: String, onResult: (Uri) -> Unit) {
        this.onResult = onResult
        launcher?.launch(options)
    }

    fun clear() {
        onResult = null
    }

    fun onDestroy() {
        launcher?.unregister()
        clear()
    }
}
