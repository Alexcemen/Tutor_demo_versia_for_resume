package ru.project.tutor.database.migrations

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Migration3To4Test {

    private val dbName = "migration-test"

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        "ru.project.tutor.database.AppDatabase",
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migrate3To4_preservesData_fillsNullDates_andDropsOrphanAttempts() {
        helper.createDatabase(dbName, 3).apply {
            execSQL(
                """
                INSERT INTO test_table (id, title, color_id, image_id, date_creation, date_last_take)
                VALUES
                (1, 'Math', 10, 20, NULL, NULL),
                (2, 'History', 11, 21, 1700000000000, 1700000005000)
                """.trimIndent()
            )
            execSQL(
                """
                INSERT INTO attempt_table (attempt_id, test_id, questions)
                VALUES
                (1, 1, '["q1"]'),
                (2, 999, '["orphan"]')
                """.trimIndent()
            )
            close()
        }

        helper.runMigrationsAndValidate(dbName, 4, true, Migration3to4()).use { db ->
            db.query(
                "SELECT title, date_creation, date_last_take FROM test_table ORDER BY id"
            ).use { cursor ->
                assertTrue(cursor.moveToFirst())
                assertEquals("Math", cursor.getString(0))
                assertFalse(cursor.isNull(1))
                assertFalse(cursor.isNull(2))

                assertTrue(cursor.moveToNext())
                assertEquals("History", cursor.getString(0))
                assertEquals(1700000000000L, cursor.getLong(1))
                assertEquals(1700000005000L, cursor.getLong(2))
                assertFalse(cursor.moveToNext())
            }

            db.query(
                "SELECT attempt_id, test_id, questions FROM attempt_table ORDER BY attempt_id"
            ).use { cursor ->
                assertTrue(cursor.moveToFirst())
                assertEquals(1, cursor.getInt(0))
                assertEquals(1, cursor.getInt(1))
                assertEquals("[\"q1\"]", cursor.getString(2))
                assertFalse(cursor.moveToNext())
            }
        }
    }
}
