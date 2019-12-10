package com.nibiruexocompany.whattodo.dagger.components

import com.nibiruexocompany.whattodo.dagger.modules.ObserversModule
import com.nibiruexocompany.whattodo.dagger.modules.RoomModule
import com.nibiruexocompany.whattodo.model.TodoItemsContainer
import com.nibiruexocompany.whattodo.view.utils.TodoItemsAdapter
import com.nibiruexocompany.whattodo.view.activities.EditTaskActivity
import com.nibiruexocompany.whattodo.view.utils.SwipeToDeleteCallback
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ObserversModule::class, RoomModule::class])
interface AppComponent {
    fun inject(todoItemsAdapter: TodoItemsAdapter)

    fun inject(todoItemsContainer: TodoItemsContainer)

    fun inject(editTaskActivity: EditTaskActivity)

    fun inject(swipeToDeleteCallback: SwipeToDeleteCallback)
}