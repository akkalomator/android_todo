package com.nibiruexocompany.whattodo.dagger.modules

import com.nibiruexocompany.whattodo.model.TodoItem
import com.nibiruexocompany.whattodo.model.TodoItemsContainer
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton

@Module
class ObserversModule {

    @Provides
    @Singleton
    fun provideTodosItemsObserver(): PublishSubject<List<TodoItem>> = PublishSubject.create<List<TodoItem>>()

    @Provides
    @Singleton
    fun provideTodoItemsConntainer(): TodoItemsContainer = TodoItemsContainer()
}
