package ru.project.tutor.domain.models.question

import kotlinx.serialization.Serializable

@Serializable
data class QuestionData(
    val id: Int,
    val questionText: String,
    val testId: Int,
    val position: Int,
    val isMultipleAnswerChoice: Boolean,
)