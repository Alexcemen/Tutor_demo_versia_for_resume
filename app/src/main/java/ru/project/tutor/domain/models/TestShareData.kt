package ru.project.tutor.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class TestShareData(
    val title: String,
    val questions: List<QuestionShareData>,
)

@Serializable
data class QuestionShareData(
    val question: String,
    val position: Int,
    val answers: List<AnswerShareData>,
)

@Serializable
data class AnswerShareData(
    val text: String,
    val isRightAnswer: Boolean,
)