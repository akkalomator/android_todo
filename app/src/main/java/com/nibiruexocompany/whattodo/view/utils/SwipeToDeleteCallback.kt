package com.nibiruexocompany.whattodo.view.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nibiruexocompany.whattodo.App
import com.nibiruexocompany.whattodo.model.TodoItemsContainer
import javax.inject.Inject

class SwipeToDeleteCallback : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    @Inject
    lateinit var todoItemsContainer: TodoItemsContainer

    init {
        App.daggerComponent.inject(this)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val viewHolder = viewHolder as TodoItemsAdapter.ItemTaskViewHolder
        todoItemsContainer.deleteItem(viewHolder.item)
    }
}