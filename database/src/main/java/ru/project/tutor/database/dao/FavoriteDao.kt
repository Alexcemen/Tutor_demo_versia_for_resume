package ru.project.tutor.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.project.tutor.database.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteEntity: FavoriteEntity): Long

    @Query("SELECT * FROM favorites_table WHERE test_id = :testId")
    fun getFavoritesByTestId(testId: Int): Flow<List<FavoriteEntity>>

    @Query("DELETE FROM favorites_table WHERE question_id = :questionId")
    suspend fun deleteFavoritesByQuestionId(questionId: Int)
}