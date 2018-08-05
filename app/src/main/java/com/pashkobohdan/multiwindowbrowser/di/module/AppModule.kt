package com.pashkobohdan.multiwindowbrowser.di.module

import android.app.Activity
import android.app.Application
import android.content.Context
import com.pashkobohdan.multiwindowbrowser.ui.activities.BrowserActivity
import com.pashkobohdan.multiwindowbrowser.ui.listeners.EmptyActivityLifecycleCallbacks
import com.pashkobohdan.multiwindowbrowser.ui.wrapper.DialogUiWrapper
import dagger.Module
import dagger.Provides

@Module
class AppModule(application: Application) {

    private var applicationContext: Context
    private lateinit var mainActivity: BrowserActivity


    init {
        applicationContext = application
        application.registerActivityLifecycleCallbacks(object : EmptyActivityLifecycleCallbacks() {
            override fun onActivityStarted(activity: Activity) {
                applicationContext = activity
                if (activity is BrowserActivity) {
                    mainActivity = activity
                }
            }
        })
    }

    @Provides
    fun provideContext(): Context {
        return applicationContext
    }

    @Provides
    fun provideDialogUiWrapper(): DialogUiWrapper {
        val context = applicationContext
        if (context is Activity) {
            return DialogUiWrapper(context);
        } else {
            throw IllegalStateException("Current context isn't activity")
        }
    }
}