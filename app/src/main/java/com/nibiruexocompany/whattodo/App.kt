package com.nibiruexocompany.whattodo

import android.app.Application
import com.nibiruexocompany.whattodo.dagger.components.DaggerTodoItemComponent
import com.nibiruexocompany.whattodo.dagger.components.TodoItemComponent

class App : Application() {

    companion object {
        lateinit var daggerComponent: TodoItemComponent
    }

    override fun onCreate() {
        super.onCreate()
        daggerComponent = DaggerTodoItemComponent.builder().build()
    }
}