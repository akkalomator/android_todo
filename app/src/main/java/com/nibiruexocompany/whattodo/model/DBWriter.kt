package com.nibiruexocompany.whattodo.model

import com.nibiruexocompany.whattodo.App
import com.nibiruexocompany.whattodo.room.TaskDB
import com.nibiruexocompany.whattodo.room.TaskEntity
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class DBWriter @Inject constructor() {

    @Inject
    lateinit var taskDB: TaskDB

    @Inject
    lateinit var todoItemsContainer: TodoItemsContainer

    init {
        App.daggerComponent.inject(this)
        val disposable = todoItemsContainer.itemsSource
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.io())
            .subscribe {
                val taskEntity = convertToTaskEntity(it)
                it.id = taskDB.taskDao().addTask(taskEntity)
            }
        val disposable2 = todoItemsContainer.itemChanged
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.io())
            .subscribe {
                val taskEntity = convertToTaskEntity(it)
                taskDB.taskDao().updateTask(taskEntity)
            }

        val disposable3 = todoItemsContainer.itemDeleted
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.io())
            .subscribe {
                val taskEntity = convertToTaskEntity(it)
                taskDB.taskDao().deleteTask(taskEntity)
            }
    }

    fun getItems(): Single<List<TodoItem>> {
        val all = taskDB.taskDao().selectAll()
        return all
            .map { list ->
                list.map {
                    TodoItem(
                        it.content,
                        it.startDate,
                        it.endDate
                    ).apply {
                        id = it.id
                        isDone = it.isDone
                    }
                }
            }
    }

    private fun convertToTaskEntity(todoItem: TodoItem): TaskEntity {
        return TaskEntity(
            todoItem.content,
            todoItem.startDate,
            todoItem.endDate,
            todoItem.isDone
        ).apply { id = todoItem.id }
    }
}