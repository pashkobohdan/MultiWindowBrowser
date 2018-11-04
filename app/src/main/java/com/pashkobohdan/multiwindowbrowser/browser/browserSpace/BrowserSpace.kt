package com.pashkobohdan.multiwindowbrowser.browser.browserSpace

import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.properties.Position
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.properties.Size
import com.pashkobohdan.multiwindowbrowser.browser.browsing.HistoryManager
import com.pashkobohdan.multiwindowbrowser.browser.browsing.Page
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpaceDTO

abstract class BrowserSpace {

    var browserSpaceDTO: BrowserSpaceDTO? = null

    internal var browserPieces = mutableSetOf<BrowserPiece>()

    fun cleanSpace() = browserPieces.clear()

    fun createNewPiece(rootPage: Page): BrowserPiece {
        val historyManager = HistoryManager()
        historyManager.goToPage(rootPage)
        val newPiece = BrowserPiece(historyManager, Size(0.0, 0.0), Position(0.0, 0.0))
        browserPieces.add(newPiece)

        initNewAddedPiece(newPiece)

        return newPiece
    }

    fun removeLastPiece() {
        if(browserPieces.size > 1) {
            browserPieces.remove(browserPieces.last())
            reInitPiecesAfterRemoving()
        }
    }

    abstract fun initNewAddedPiece(newPiece: BrowserPiece)

    abstract fun reInitPiecesAfterRemoving()

    abstract fun canChangePieceSize(): Boolean

    abstract fun canChangePiecePosition(): Boolean
}