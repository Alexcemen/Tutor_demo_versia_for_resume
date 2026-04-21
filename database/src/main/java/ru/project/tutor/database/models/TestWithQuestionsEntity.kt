package ru.project.tutor.database.models

import androidx.room.Embedded
import androidx.room.Relation
import ru.project.tutor.database.entity.ErrorEntity
import ru.project.tutor.database.entity.FavoriteEntity
import ru.project.tutor.database.entity.QuestionEntity
import ru.project.tutor.database.entity.TestEntity

data class TestWithQuestionsEntity(
    @Embedded
    val test: TestEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "test_id"
    )
    val questions: List<QuestionEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "test_id"
    )
    val errors: List<ErrorEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "test_id"
    )
    val favorites: List<FavoriteEntity>,
)