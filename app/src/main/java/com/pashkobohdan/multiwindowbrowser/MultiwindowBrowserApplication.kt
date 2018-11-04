package com.pashkobohdan.multiwindowbrowser

import android.app.Application
import com.pashkobohdan.multiwindowbrowser.di.ApplicationComponent
import com.pashkobohdan.multiwindowbrowser.di.DaggerApplicationComponent
import com.pashkobohdan.multiwindowbrowser.di.module.AppModule
import com.pashkobohdan.multiwindowbrowser.di.module.DatabaseModule

class MultiwindowBrowserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initializeDagger()
    }

    private fun initializeDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .appModule(AppModule(this))
                .databaseModule(DatabaseModule(this))
                .build()
    }

    companion object {
        lateinit var INSTANCE: MultiwindowBrowserApplication
        lateinit var applicationComponent: ApplicationComponent
    }
}