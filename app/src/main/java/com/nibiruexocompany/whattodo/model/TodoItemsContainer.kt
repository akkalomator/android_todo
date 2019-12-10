package com.nibiruexocompany.whattodo.model

import com.nibiruexocompany.whattodo.App
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class TodoItemsContainer {
    private var items: MutableList<TodoItem> = ArrayList()

    @Inject
    lateinit var itemsDistributor: PublishSubject<List<TodoItem>>

    init {
        App.daggerComponent.inject(this)
    }

    fun addItem(item: TodoItem) {
        items.add(item)
        itemsDistributor.onNext(items)
        val disposable = item.dataChanged.subscribe {
            itemsDistributor.onNext(items)
        }
    }
}