package com.nibiruexocompany.whattodo.dagger.components

import com.nibiruexocompany.whattodo.App
import com.nibiruexocompany.whattodo.dagger.modules.ObserversModule
import com.nibiruexocompany.whattodo.dagger.modules.RecyclerViewAdapterModule
import com.nibiruexocompany.whattodo.dagger.modules.RoomModule
import com.nibiruexocompany.whattodo.model.DBWriter
import com.nibiruexocompany.whattodo.model.TodoItemsContainer
import com.nibiruexocompany.whattodo.view.utils.TodoItemsAdapter
import com.nibiruexocompany.whattodo.view.activities.EditTaskActivity
import com.nibiruexocompany.whattodo.view.activities.MainActivity
import com.nibiruexocompany.whattodo.view.utils.SwipeToDeleteCallback
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ObserversModule::class, RecyclerViewAdapterModule::class, RoomModule::class])
interface AppComponent {

    // App
    fun inject(app: App)

    // Activities
    fun inject(mainActivity: MainActivity)
    fun inject(editTaskActivity: EditTaskActivity)

    // Utils
    fun inject(todoItemsAdapter: TodoItemsAdapter)
    fun inject(swipeToDeleteCallback: SwipeToDeleteCallback)

    // Model
    fun inject(todoItemsContainer: TodoItemsContainer)

    fun inject(dbWriter: DBWriter)
}