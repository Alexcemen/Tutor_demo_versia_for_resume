package ru.project.tutor.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.project.tutor.database.dao.AnswerChoiceDao
import ru.project.tutor.database.dao.AttemptDao
import ru.project.tutor.database.dao.ErrorDao
import ru.project.tutor.database.dao.FavoriteDao
import ru.project.tutor.database.dao.QuestionDao
import ru.project.tutor.database.dao.TestDao
import ru.project.tutor.database.entity.AnswerChoiceEntity
import ru.project.tutor.database.entity.AttemptEntity
import ru.project.tutor.database.entity.ErrorEntity
import ru.project.tutor.database.entity.FavoriteEntity
import ru.project.tutor.database.entity.QuestionEntity
import ru.project.tutor.database.entity.TestEntity
import ru.project.tutor.database.migrations.Migration2to3
import ru.project.tutor.database.migrations.Migration3to4

private const val DATABASE_VERSION = 4

@Database(
    entities = [
        TestEntity::class,
        QuestionEntity::class,
        AnswerChoiceEntity::class,
        AttemptEntity::class,
        ErrorEntity::class,
        FavoriteEntity::class
    ],
    version = DATABASE_VERSION,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = Migration2to3::class),
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun testDao(): TestDao
    abstract fun questionDao(): QuestionDao
    abstract fun answerChoiceDao(): AnswerChoiceDao
    abstract fun attemptDao(): AttemptDao
    abstract fun errorDao(): ErrorDao
    abstract fun favoriteDao(): FavoriteDao
}

fun createDatabase(context: Context) = Room.databaseBuilder(
    context,
    AppDatabase::class.java, "tutor-database"
).addMigrations(Migration3to4())
    .build()
