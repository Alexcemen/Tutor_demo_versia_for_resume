package ru.project.tutor.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration3to4 : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        val now = System.currentTimeMillis()

        db.execSQL(
            """
            CREATE TABLE test_table_new (                                                                                                                                  
                  id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,                                                                                                             
                  title TEXT NOT NULL,           
                  color_id INTEGER NOT NULL,
                  image_id INTEGER NOT NULL,                                                                                                                                 
                  date_creation INTEGER NOT NULL,                                                                                                                            
                  date_last_take INTEGER NOT NULL                                                                                                                            
              ) 
        """
        )

        db.execSQL(
            """
             INSERT INTO test_table_new (id, title, color_id, image_id, date_creation, date_last_take)
              SELECT id, title, color_id, image_id,                                                                                                                          
                     COALESCE(date_creation, $now), 
                     COALESCE(date_last_take, $now)                                                                                                                          
              FROM test_table    
        """
        )

        db.execSQL("DROP TABLE test_table")
        db.execSQL("ALTER TABLE test_table_new RENAME TO test_table")

        db.execSQL(
            """
            CREATE TABLE attempt_table_new (
                attempt_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                test_id INTEGER NOT NULL,
                questions TEXT NOT NULL,
                FOREIGN KEY(test_id) REFERENCES test_table(id) ON UPDATE NO ACTION ON DELETE CASCADE
            )
        """
        )

        db.execSQL(
            """
            INSERT INTO attempt_table_new (attempt_id, test_id, questions)
            SELECT attempt_table.attempt_id, attempt_table.test_id, attempt_table.questions
            FROM attempt_table
            INNER JOIN test_table ON test_table.id = attempt_table.test_id
        """
        )

        db.execSQL("DROP TABLE attempt_table")
        db.execSQL("ALTER TABLE attempt_table_new RENAME TO attempt_table")

        db.execSQL("CREATE INDEX index_question_table_test_id ON question_table(test_id)")
        db.execSQL("CREATE INDEX index_answer_choice_table_question_id ON answer_choice_table(question_id)")
        db.execSQL("CREATE INDEX index_attempt_table_test_id ON attempt_table(test_id)")
        db.execSQL("CREATE INDEX index_favorites_table_test_id ON favorites_table(test_id)")
        db.execSQL("CREATE INDEX index_errors_table_test_id ON errors_table(test_id)")
    }
}
