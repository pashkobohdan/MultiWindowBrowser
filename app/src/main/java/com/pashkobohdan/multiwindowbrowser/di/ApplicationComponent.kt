package com.pashkobohdan.multiwindowbrowser.di

import com.pashkobohdan.multiwindowbrowser.di.module.AppModule
import com.pashkobohdan.multiwindowbrowser.ui.activities.BrowserActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface ApplicationComponent {

    fun inject(activity: BrowserActivity)
}