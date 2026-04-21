package ru.project.tutor.ui.screen.test_manager.models

import androidx.compose.ui.text.AnnotatedString

data class AnswerChoiceHighlightTextUi(
    val id: Int,
    val highlightText: AnnotatedString,
    val isRightAnswer: Boolean,
)