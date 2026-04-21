package ru.project.tutor.domain.models.question_with_answers

import ru.project.tutor.domain.models.answer_choice.AnswerChoiceData
import ru.project.tutor.domain.models.question.QuestionData

data class QuestionWithAnswersData(
    val question: QuestionData,
    val answers: List<AnswerChoiceData>
)
