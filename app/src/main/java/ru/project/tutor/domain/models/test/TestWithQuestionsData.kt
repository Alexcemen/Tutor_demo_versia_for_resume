package ru.project.tutor.domain.models.test

import ru.project.tutor.domain.models.question_with_answers.QuestionWithAnswersData

data class TestWithQuestionsData(
    val testData: TestData,
    val questions: List<QuestionWithAnswersData>,
)