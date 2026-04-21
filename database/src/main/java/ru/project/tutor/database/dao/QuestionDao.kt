package ru.project.tutor.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.project.tutor.database.entity.QuestionEntity

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(questionEntity: QuestionEntity): Long

    @Query("SELECT * FROM question_table WHERE id = :id")
    suspend fun get(id: Int): QuestionEntity?

    @Query("SELECT * FROM question_table WHERE test_id = :testId")
    suspend fun getQuestionsByTestId(testId: Int): List<QuestionEntity>

    @Query("DELETE FROM question_table WHERE id = :questionId")
    suspend fun deleteById(questionId: Int)

    @Query("DELETE FROM question_table WHERE test_id = :testId")
    suspend fun deleteAllQuestionsByTestId(testId: Int)

    @Query("SELECT id FROM question_table WHERE test_id = :testId")
    suspend fun getQuestionIdsByTestID(testId: Int): List<Int>

    @Query("UPDATE question_table SET question = :question WHERE id = :questionId")
    suspend fun updateQuestionText(
        questionId: Int,
        question: String,
    )

    @Query("SELECT MAX(position) FROM question_table WHERE test_id = :testId")
    suspend fun getMaxPosition(testId: Int): Int?

    @Query("UPDATE question_table SET position = position - 1 WHERE test_id = :testId AND position > :deletedPosition")
    suspend fun updatePositions(testId: Int, deletedPosition: Int)

    @Query("SELECT position FROM question_table WHERE id = :questionId")
    suspend fun getPositionByQuestionId(questionId: Int): Int
}