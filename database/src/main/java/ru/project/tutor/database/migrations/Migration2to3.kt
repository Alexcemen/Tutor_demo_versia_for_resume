package ru.project.tutor.database.migrations

import androidx.room.DeleteColumn
import androidx.room.migration.AutoMigrationSpec

@DeleteColumn("question_table", columnName = "is_multiple_answer_choice")
class Migration2to3 : AutoMigrationSpec