package ru.project.tutor.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class ClipboardWrapper(
    private val ctx: Context,
) {
    val clipboardManager = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    fun copyToClipboard(data: String) {
        clipboardManager.setPrimaryClip(
            ClipData.newPlainText(
                LABEL,
                data
            )
        )
    }

    fun getFromClipboard(): String {
        val clip = clipboardManager.primaryClip ?: return ""

        if (clip.itemCount > 0) {
            return clip.getItemAt(0).text?.toString().orEmpty()
        }
        return ""
    }

    private companion object {
        const val LABEL = "promt"
    }
}
