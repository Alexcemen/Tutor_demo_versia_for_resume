package ru.project.tutor.ui.screen.test_manager.models

import androidx.compose.ui.text.AnnotatedString

data class QuestionInfoCardHighlightTextUi(
    val questionId: Int,
    val position: Int,
    val questionNumber: Int,
    val questionText: AnnotatedString,
    val answerChoices: List<AnswerChoiceHighlightTextUi>,
)