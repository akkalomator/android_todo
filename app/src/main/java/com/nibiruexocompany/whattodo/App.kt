package com.nibiruexocompany.whattodo

import android.app.Application
import android.util.Log
import com.amitshekhar.DebugDB
import com.nibiruexocompany.whattodo.dagger.components.DaggerAppComponent
import com.nibiruexocompany.whattodo.dagger.components.AppComponent
import com.nibiruexocompany.whattodo.dagger.modules.RoomModule
import com.nibiruexocompany.whattodo.model.DBWriter
import com.nibiruexocompany.whattodo.room.TaskDB
import javax.inject.Inject

class App : Application() {

    companion object {
        private const val TAG = "App"
        lateinit var daggerComponent: AppComponent
    }

    @Inject
    lateinit var dbWriter: DBWriter

    override fun onCreate() {
        super.onCreate()
        daggerComponent = DaggerAppComponent.builder().roomModule(RoomModule(this)).build()
        daggerComponent.inject(this)
        Log.i(TAG, DebugDB.getAddressLog())
    }
}