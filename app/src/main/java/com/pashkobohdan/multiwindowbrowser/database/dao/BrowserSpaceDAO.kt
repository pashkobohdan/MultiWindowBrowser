package com.pashkobohdan.multiwindowbrowser.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserPieceDTO
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpaceDTO
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpacePreviewImageDTO

@Dao
interface BrowserSpaceDAO {

    @get:Query("SELECT * FROM BrowserSpaceDTO")
    val allBrowserSpaces: List<BrowserSpaceDTO>

    @get:Query("SELECT * FROM BrowserPieceDTO")
    val allBrowserPieces: List<BrowserPieceDTO>

    @Query("SELECT * FROM BrowserSpacePreviewImageDTO WHERE id = :id ")
    fun getBrowserSpacePreviewImageDTOById(id:Long): List<BrowserSpacePreviewImageDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllBrowserSpaceDTOs(vararg bookDTOS: BrowserSpaceDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllBrowserPieceDTOs(vararg bookDTOS: BrowserPieceDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBrowserSpacePreviewImageDTO(vararg previewImageDTO: BrowserSpacePreviewImageDTO)
}