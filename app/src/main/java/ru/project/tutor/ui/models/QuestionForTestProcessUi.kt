package ru.project.tutor.ui.models

import kotlinx.serialization.Serializable
import ru.project.tutor.domain.models.question.QuestionData
import ru.project.tutor.ui.screen.test_process.composable.QuestionStatus

@Serializable
data class QuestionForTestProcessUi(
    val question: QuestionData,
    val answers: List<AnswerChoiceDataUi>,
    val questionStatus: QuestionStatus,
    val isQuestionAnsweredBefore: Boolean,
    val isFavorite: Boolean,
    val isMultipleAnswerChoice: Boolean,
)