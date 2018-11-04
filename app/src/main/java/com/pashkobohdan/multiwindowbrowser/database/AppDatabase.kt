package com.pashkobohdan.multiwindowbrowser.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.pashkobohdan.multiwindowbrowser.database.dao.BrowserSpaceDAO
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserPieceDTO
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpaceDTO
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpacePreviewImageDTO
import com.pashkobohdan.multiwindowbrowser.database.dto.Converters

@Database(entities = [BrowserSpaceDTO::class, BrowserPieceDTO::class, BrowserSpacePreviewImageDTO::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getBrowserSpaceDAO(): BrowserSpaceDAO
}