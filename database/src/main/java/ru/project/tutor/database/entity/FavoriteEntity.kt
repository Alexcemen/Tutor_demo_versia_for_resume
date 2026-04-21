package ru.project.tutor.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "favorites_table",
    indices = [Index("test_id")],
    primaryKeys = ["question_id"],
    foreignKeys = [ForeignKey(
        entity = QuestionEntity::class,
        parentColumns = ["id"],
        childColumns = ["question_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class FavoriteEntity(
    @ColumnInfo(name = "test_id")
    val testId: Int,
    @ColumnInfo(name = "question_id")
    val questionId: Int,
)