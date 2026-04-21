package ru.project.tutor.ui.models

import ru.project.tutor.domain.models.question.QuestionData
import ru.project.tutor.ui.screen.test_result.compose.QuestionStatusTestResult

data class QuestionWorkoutUi(
    val question: QuestionData,
    val answers: List<AnswerChoiceDataUi>,
    val questionStatus: QuestionStatusTestResult,
)