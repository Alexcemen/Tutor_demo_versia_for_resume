package ru.project.tutor.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.project.tutor.database.entity.AnswerChoiceEntity

@Dao
interface AnswerChoiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answerChoiceEntity: List<AnswerChoiceEntity>): List<Long>

    @Query("SELECT * FROM answer_choice_table WHERE question_id = :questionId")
    suspend fun getByQuestionId(questionId: Int): List<AnswerChoiceEntity>

    @Query("DELETE FROM answer_choice_table WHERE question_id = :questionId")
    suspend fun deleteByQuestionId(questionId: Int)
}