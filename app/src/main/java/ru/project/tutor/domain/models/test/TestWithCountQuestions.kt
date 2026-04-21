package ru.project.tutor.domain.models.test

data class TestWithCountQuestions(
    val testData: TestData,
    val normalQuestionsCount: Int,
    val favoriteQuestionsCount: Int,
    val errorQuestionsCount: Int,
)
