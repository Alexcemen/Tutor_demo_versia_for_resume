package ru.project.tutor.ui.screen.test_manager

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

fun highlightText(
    text: String,
    query: String,
    highlightColor: Color = Color.Yellow.copy(alpha = 0.3f),
) = buildAnnotatedString {

    if (query.isEmpty()) {
        append(text)
        return@buildAnnotatedString
    }

    val lowerText = text.lowercase()
    val lowerQuery = query.lowercase()
    val queryLength = query.length

    val firstIndex = lowerText.indexOf(lowerQuery)

    if (firstIndex == -1) {
        append(text)
        return@buildAnnotatedString
    }

    var startIndex = 0

    generateSequence(lowerText.indexOf(lowerQuery)) { prevIndex ->
        lowerText.indexOf(lowerQuery, prevIndex + queryLength)
            .takeIf { it != -1 }
    }.forEach { matchIndex ->

        if (matchIndex > startIndex) {
            append(text.substring(startIndex, matchIndex))
        }

        withStyle(SpanStyle(background = highlightColor)) {
            append(text.substring(matchIndex, matchIndex + queryLength))
        }

        startIndex = matchIndex + queryLength
    }

    if (startIndex < text.length) {
        append(text.substring(startIndex))
    }
}