package com.pashkobohdan.multiwindowbrowser

import android.app.Application
import com.pashkobohdan.multiwindowbrowser.di.ApplicationComponent
import com.pashkobohdan.multiwindowbrowser.di.DaggerApplicationComponent

class MultiwindowBrowserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initializeDagger()
    }

    private fun initializeDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .build()
    }

    companion object {
        lateinit var INSTANCE: MultiwindowBrowserApplication
        lateinit var applicationComponent: ApplicationComponent
    }
}