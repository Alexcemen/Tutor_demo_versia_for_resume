package ru.project.tutor.domain.models.test

data class TestData(
    val id: Int,
    val title: String,
    val colorId: Int,
    val imageId: Int,
    val dateLastTake: Long,
    val dateCreation: Long,
)