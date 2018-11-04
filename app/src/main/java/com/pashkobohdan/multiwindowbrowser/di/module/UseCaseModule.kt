package com.pashkobohdan.multiwindowbrowser.di.module

import com.pashkobohdan.ttsreader.data.usecase.scheduler.ThreadPoolScheduler
import com.pashkobohdan.ttsreader.data.usecase.scheduler.impl.ThreadPoolSchedulerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideThreadPoolScheduler(): ThreadPoolScheduler {
        return ThreadPoolSchedulerImpl()
    }
}