package com.nibiruexocompany.whattodo.dagger.modules

import com.nibiruexocompany.whattodo.view.utils.TodoItemsAdapter
import dagger.Module
import dagger.Provides

@Module
class RecyclerViewAdapterModule {

    @Provides
    fun provideTodoItemAdapter() = TodoItemsAdapter()
}