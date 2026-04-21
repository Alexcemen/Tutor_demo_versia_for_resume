package ru.project.tutor.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import ru.project.tutor.database.entity.TestEntity

data class TestWithCountsEntity(
    @Embedded
    val test: TestEntity,
    @ColumnInfo(name = "questions_count")
    val questionsCount: Int,
    @ColumnInfo(name = "favorites_count")
    val favoritesCount: Int,
    @ColumnInfo(name = "errors_count")
    val errorsCount: Int,
)