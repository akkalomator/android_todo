package com.nibiruexocompany.whattodo.model

import com.nibiruexocompany.whattodo.App
import io.reactivex.subjects.PublishSubject

class TodoItemsContainer {
    private val items: MutableList<TodoItem> = ArrayList()

    val itemsSet: PublishSubject<List<TodoItem>> = PublishSubject.create()
    val itemsSource: PublishSubject<TodoItem> = PublishSubject.create()
    val itemChanged: PublishSubject<TodoItem> = PublishSubject.create()
    val itemDeleted: PublishSubject<TodoItem> = PublishSubject.create()

    init {
        App.daggerComponent.inject(this)
    }

    fun setItems(items: List<TodoItem>) {
        this.items.addAll(items)
        items.forEach { item -> item.dataChanged.subscribe { itemChanged.onNext(item) } }
        itemsSet.onNext(items)
    }

    fun get(id: Long) = items.find { it.id == id }

    fun addItem(item: TodoItem) {
        items.add(item)
        itemsSource.onNext(item)
        val disposable = item.dataChanged.subscribe {
            itemChanged.onNext(item)
        }
    }

    fun replaceItem(item: TodoItem) {
        val index = items.indexOfFirst { it.id == item.id }
        items[index] = item
        itemChanged.onNext(item)
    }

    fun deleteItem(item: TodoItem) {
        items.remove(item)
        itemDeleted.onNext(item)
        item.dataChanged.onComplete()
    }

    fun completedTasksCount() = items.count { it.isDone }
    fun totalTasksCount() = items.size
}