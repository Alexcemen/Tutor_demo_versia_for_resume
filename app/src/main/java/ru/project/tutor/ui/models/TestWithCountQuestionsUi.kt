package ru.project.tutor.ui.models

import ru.project.tutor.domain.models.test.TestData

data class TestWithCountQuestionsUi(
    val testData: TestData,
    val countQuestions: Int,
)
