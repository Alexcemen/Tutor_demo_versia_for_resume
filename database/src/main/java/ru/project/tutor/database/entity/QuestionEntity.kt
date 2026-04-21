package ru.project.tutor.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "question_table",
    indices = [Index("test_id")],
    foreignKeys = [ForeignKey(
        entity = TestEntity::class,
        parentColumns = ["id"],
        childColumns = ["test_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "test_id")
    val testId: Int,
    @ColumnInfo(name = "position")
    val position: Int,
)