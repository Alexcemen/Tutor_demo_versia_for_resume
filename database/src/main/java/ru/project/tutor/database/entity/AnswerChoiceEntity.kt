package ru.project.tutor.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "answer_choice_table",
    indices = [Index("question_id")],
    foreignKeys = [ForeignKey(
        entity = QuestionEntity::class,
        parentColumns = ["id"],
        childColumns = ["question_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class AnswerChoiceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "answer_choice")
    val answerChoice: String,
    @ColumnInfo(name = "is_right_answer")
    val isRightAnswer: Boolean,
    @ColumnInfo(name = "question_id")
    val questionId: Int,
)