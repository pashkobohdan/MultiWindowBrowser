package com.pashkobohdan.multiwindowbrowser.database.dto

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.properties.Position
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.properties.Size
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.BrowserSpace
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.impl.HorizontalPartsBrowserSpace
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.impl.VerticalPartsBrowserSpace
import com.pashkobohdan.multiwindowbrowser.browser.browsing.HistoryManager
import com.pashkobohdan.multiwindowbrowser.browser.browsing.Page

@Entity(tableName = "BrowserPieceDTO")
data class BrowserPieceDTO(

        @PrimaryKey
        var id: Long,

//        @Embedded
//        @SerializedName("historyList")
        var historyList: List<String>,

        var width: Double,
        var height: Double,
        var startX: Double,
        var startY: Double

) {
    constructor() : this(System.currentTimeMillis(), listOf(), .0, .0, .0, .0)
}

@Entity(tableName = "BrowserSpaceDTO")
data class BrowserSpaceDTO(

        @PrimaryKey
        var id: Long,

        var browserPieces: List<BrowserPieceDTO>,

        var spaceType: String,

        var spaceName: String,

        @Ignore
        var previewImage: BrowserSpacePreviewImageDTO?
) {
    constructor() : this(System.currentTimeMillis(), listOf(), "", "", null)
}

@Entity(tableName = "BrowserSpacePreviewImageDTO")
data class BrowserSpacePreviewImageDTO(

        @PrimaryKey
        var id: Long,

        var bitmapString: String
) {
    constructor() : this(System.currentTimeMillis(), "")
}

enum class BrowserSpaceType {
    HORIZONTAL_EQUAL_PARTS,
    VERTICAL_EQUAL_PARTS
}

object BrowserSpaceToBrowserSpaceDTOConverter {

    fun convertToDTO(browserSpace: BrowserSpace): BrowserSpaceDTO {
        val spaceDTO = browserSpace.browserSpaceDTO ?: BrowserSpaceDTO()
        spaceDTO.browserPieces = browserSpace.browserPieces.map { piece ->
            val pieceDTO = BrowserPieceDTO()
            pieceDTO.historyList = piece.historyManager.previousPages.map { it.url }
            pieceDTO.width = piece.size.width
            pieceDTO.height = piece.size.height
            pieceDTO.startX = piece.position.startX
            pieceDTO.startY = piece.position.startY
            return@map pieceDTO
        }
        spaceDTO.spaceType = getBrowserSpaceType(browserSpace).name
        return spaceDTO
    }

    fun convertToSpace(browserSpaceDTO: BrowserSpaceDTO): BrowserSpace {
        val space = createBrowserBySpaceType(BrowserSpaceType.valueOf(browserSpaceDTO.spaceType))
        space.browserSpaceDTO = browserSpaceDTO
        space.browserPieces = browserSpaceDTO.browserPieces.map { pieceDTO ->
            val historyManager = HistoryManager()
            pieceDTO.historyList.forEach { page ->
                historyManager.goToPage(Page(page))
            }
            val piece = BrowserPiece(
                    historyManager,
                    Size(pieceDTO.width, pieceDTO.height),
                    Position(pieceDTO.startX, pieceDTO.startY))

            return@map piece
        }.toMutableSet()
        return space
    }

    private fun getBrowserSpaceType(browserSpace: BrowserSpace): BrowserSpaceType {
        return when (browserSpace) {
            is HorizontalPartsBrowserSpace -> BrowserSpaceType.HORIZONTAL_EQUAL_PARTS
            is VerticalPartsBrowserSpace -> BrowserSpaceType.VERTICAL_EQUAL_PARTS
            else -> throw IllegalArgumentException("Unsupported space type ${browserSpace}")
        }
    }

    private fun createBrowserBySpaceType(browserSpaceType: BrowserSpaceType): BrowserSpace {
        return when (browserSpaceType) {
            BrowserSpaceType.HORIZONTAL_EQUAL_PARTS -> HorizontalPartsBrowserSpace()
            BrowserSpaceType.VERTICAL_EQUAL_PARTS -> VerticalPartsBrowserSpace()
        }
    }
}

class Converters {

    @TypeConverter
    fun convertStringList(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {

        }.getType()
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun convertPieceList(list: List<BrowserPieceDTO>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toPieceList(value: String): List<BrowserPieceDTO> {
        val listType = object : TypeToken<List<BrowserPieceDTO>>() {

        }.getType()
        return Gson().fromJson(value, listType)
    }

}