package com.nibiruexocompany.whattodo.model

import com.nibiruexocompany.whattodo.App
import io.reactivex.subjects.PublishSubject

class TodoItemsContainer {
    private val items: MutableList<TodoItem> = ArrayList()

    val itemsSource: PublishSubject<TodoItem> = PublishSubject.create()
    val itemChanged: PublishSubject<TodoItem> = PublishSubject.create()
    val itemDeleted: PublishSubject<TodoItem> = PublishSubject.create()

    init {
        App.daggerComponent.inject(this)
    }

    fun addItem(item: TodoItem) {
        items.add(item)
        itemsSource.onNext(item)
        val disposable = item.dataChanged.subscribe {
            itemChanged.onNext(item)
        }
    }

    fun deleteItem(item: TodoItem) {
        items.remove(item)
        itemDeleted.onNext(item)
        item.dataChanged.onComplete()
    }
}