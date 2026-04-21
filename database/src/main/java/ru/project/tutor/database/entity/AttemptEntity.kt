package ru.project.tutor.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "attempt_table",
    indices = [Index("test_id")],
    foreignKeys = [ForeignKey(
        entity = TestEntity::class,
        parentColumns = ["id"],
        childColumns = ["test_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class AttemptEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "attempt_id")
    val attemptId: Int = 0,
    @ColumnInfo(name = "test_id")
    val testId: Int,
    @ColumnInfo(name = "questions")
    val questions: String,
)