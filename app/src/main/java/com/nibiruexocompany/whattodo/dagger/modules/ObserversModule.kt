package com.nibiruexocompany.whattodo.dagger.modules

import com.nibiruexocompany.whattodo.model.TodoItemsContainer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ObserversModule {

    @Provides
    @Singleton
    fun provideTodoItemsContainer(): TodoItemsContainer = TodoItemsContainer()
}
