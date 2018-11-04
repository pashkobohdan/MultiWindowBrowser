package com.pashkobohdan.multiwindowbrowser.di.module

import android.app.Application
import android.arch.persistence.room.Room
import com.pashkobohdan.multiwindowbrowser.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(application: Application) {

    private var database: AppDatabase

    init {
        database = Room.databaseBuilder(application.applicationContext, AppDatabase::class.java,
                "browser_space_database").build()
    }

    @Provides
    @Singleton
    fun provideAppDatabase() = database
}