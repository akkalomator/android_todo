package com.nibiruexocompany.whattodo.dagger.components

import com.nibiruexocompany.whattodo.dagger.modules.ObserversModule
import com.nibiruexocompany.whattodo.model.TodoItemsContainer
import com.nibiruexocompany.whattodo.view.implementation.activities.MainActivity
import com.nibiruexocompany.whattodo.view.adapters.TodoItemsAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ObserversModule::class])
interface TodoItemComponent {
    fun inject(todoItemsAdapter: TodoItemsAdapter)

    fun inject(mainActivity: MainActivity)

    fun inject(todoItemsContainer: TodoItemsContainer)
}