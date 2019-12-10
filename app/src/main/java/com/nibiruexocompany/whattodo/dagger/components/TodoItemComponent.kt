package com.nibiruexocompany.whattodo.dagger.components

import com.nibiruexocompany.whattodo.dagger.modules.ObserversModule
import com.nibiruexocompany.whattodo.model.TodoItemsContainer
import com.nibiruexocompany.whattodo.view.adapters.TodoItemsAdapter
import com.nibiruexocompany.whattodo.view.activities.EditTaskActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ObserversModule::class])
interface TodoItemComponent {
    fun inject(todoItemsAdapter: TodoItemsAdapter)

    fun inject(todoItemsContainer: TodoItemsContainer)

    fun inject(editTaskActivity: EditTaskActivity)
}