package com.nibiruexocompany.whattodo.room

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun selectAll() : Single<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(taskEntity: TaskEntity) : Long

    @Delete
    fun deleteTask(taskEntity: TaskEntity)

    @Update
    fun updateTask(taskEntity: TaskEntity)
}