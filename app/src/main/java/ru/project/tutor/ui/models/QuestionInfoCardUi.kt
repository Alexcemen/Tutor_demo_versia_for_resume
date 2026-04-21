package ru.project.tutor.ui.models

data class QuestionInfoCardUi(
    val questionId: Int,
    val position: Int,
    val questionNumber: Int,
    val questionText: String,
    val answerChoices: List<AnswerChoiceUi>,
)