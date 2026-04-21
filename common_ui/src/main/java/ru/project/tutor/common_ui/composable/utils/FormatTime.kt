package ru.project.tutor.common_ui.composable.utils


fun formatTime(ms: Long): String {
    var seconds = ms / 1000
    val hours = seconds / 3600
    seconds %= 3600
    val minutes = seconds / 60
    seconds %= 60

    return when {
        hours > 0 -> "${hours}ч${minutes}м"
        minutes > 0 -> "${minutes}м${seconds}с"
        else -> "${seconds}с"
    }
}