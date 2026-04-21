package ru.project.tutor.domain.models.answer_choice

import kotlinx.serialization.Serializable

@Serializable
data class AnswerChoiceData(
    val id: Int,
    val text: String,
    val isRightAnswer: Boolean,
    val questionId: Int,
)