package ru.project.tutor.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.project.tutor.database.entity.AttemptEntity

@Dao
interface AttemptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attemptEntity: AttemptEntity): Long

    @Query("SELECT questions FROM attempt_table WHERE attempt_id = :attemptId")
    suspend fun getQuestions(attemptId: Int): String
}