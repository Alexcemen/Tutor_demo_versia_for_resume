package ru.project.tutor.ui.models

import kotlinx.serialization.Serializable
import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData

@Serializable
data class AnswerChoiceDataUi(
    val answerChoiceData: AnswerChoiceData,
    val isSelectedAnswer: Boolean = false,
)