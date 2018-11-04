package com.pashkobohdan.multiwindowbrowser.di

import com.pashkobohdan.multiwindowbrowser.di.module.AppModule
import com.pashkobohdan.multiwindowbrowser.di.module.DatabaseModule
import com.pashkobohdan.multiwindowbrowser.di.module.UseCaseModule
import com.pashkobohdan.multiwindowbrowser.ui.activities.SplashActivity
import com.pashkobohdan.multiwindowbrowser.ui.activities.browserSpaceList.BrowserSpaceListActivity
import com.pashkobohdan.multiwindowbrowser.ui.activities.space.BrowserActivity
import com.pashkobohdan.multiwindowbrowser.ui.browserUiCreator.BrowserUiCreatorFactory
import com.pashkobohdan.multiwindowbrowser.ui.pieceCreator.UICreator
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, DatabaseModule::class, UseCaseModule::class])
@Singleton
interface ApplicationComponent {

    fun inject(activity: SplashActivity)

    fun inject(activity: BrowserActivity)

    fun inject(factory: BrowserUiCreatorFactory)

    fun inject(uiCreator: UICreator)

    fun inject(activity: BrowserSpaceListActivity)
}