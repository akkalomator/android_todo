package com.nibiruexocompany.whattodo.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskEntity::class], version = 2, exportSchema = false)
@TypeConverters(CalendarConverter::class)
abstract class TaskDB : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}