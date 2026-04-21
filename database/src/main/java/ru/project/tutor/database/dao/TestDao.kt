package ru.project.tutor.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.project.tutor.database.entity.TestEntity
import ru.project.tutor.database.models.TestWithCountsEntity
import ru.project.tutor.database.models.TestWithQuestionsEntity

@Dao
interface TestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(testEntity: TestEntity): Long

    @Query("SELECT * FROM test_table WHERE id = :id")
    suspend fun get(id: Int): TestEntity

    @Query("SELECT * FROM test_table")
    fun getAll(): Flow<List<TestEntity>>

    @Query("DELETE FROM test_table WHERE id = :id")
    suspend fun deleteTest(id: Int)

    @Transaction
    @Query("SELECT * FROM test_table")
    fun getTestsWithQuestions(): Flow<List<TestWithQuestionsEntity>>

    @Query(
        """
        SELECT t.*,
            (SELECT COUNT(*) FROM question_table WHERE test_id = t.id) as questions_count,
            (SELECT COUNT(*) FROM favorites_table WHERE test_id = t.id) as favorites_count,
            (SELECT COUNT(*) FROM errors_table WHERE test_id = t.id) as errors_count
        FROM test_table t
    """
    )
    fun getTestsWithCounts(): Flow<List<TestWithCountsEntity>>

    @Query("SELECT date_creation FROM test_table WHERE id = :id")
    suspend fun getDateCreation(id: Int): Long

    @Query("SELECT date_last_take FROM test_table WHERE id = :id")
    suspend fun getDateLastTake(id: Int): Long

    @Query("UPDATE test_table SET date_last_take = :dateLastTake WHERE id = :id")
    suspend fun setDateLastTake(id: Int, dateLastTake: Long)
}
