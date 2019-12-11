package com.nibiruexocompany.whattodo.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "tasks")
data class TaskEntity (
    val content: String,
    val startDate: Calendar?,
    val endDate: Calendar?,
    val isDone: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
