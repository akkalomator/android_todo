package com.nibiruexocompany.whattodo.dagger.modules

import android.content.Context
import androidx.room.Room
import com.nibiruexocompany.whattodo.model.DBWriter
import com.nibiruexocompany.whattodo.room.TaskDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideTaskDb(): TaskDB =
        Room
            .databaseBuilder(
                context,
                TaskDB::class.java, "tasks"
            )
            .fallbackToDestructiveMigration()
            .build()
}