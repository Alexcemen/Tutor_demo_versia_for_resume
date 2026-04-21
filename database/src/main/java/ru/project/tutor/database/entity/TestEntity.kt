package ru.project.tutor.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_table")
data class TestEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "color_id")
    val colorId: Int,
    @ColumnInfo(name = "image_id")
    val imageId: Int,
    @ColumnInfo(name = "date_creation")
    val dateCreation: Long,
    @ColumnInfo(name = "date_last_take")
    val dateLastTake: Long,
)