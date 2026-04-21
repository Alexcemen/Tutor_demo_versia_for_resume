package ru.project.tutor.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.project.tutor.database.entity.ErrorEntity

@Dao
interface ErrorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(errorEntity: ErrorEntity): Long

    @Query("SELECT * FROM errors_table WHERE test_id = :testId")
    fun getErrorsByTestId(testId: Int): Flow<List<ErrorEntity>>

    @Query("DELETE FROM errors_table WHERE question_id = :questionId")
    suspend fun deleteErrorsByQuestionId(questionId: Int)
}