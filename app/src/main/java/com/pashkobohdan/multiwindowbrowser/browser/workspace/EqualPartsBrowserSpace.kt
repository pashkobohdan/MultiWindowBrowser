package com.pashkobohdan.multiwindowbrowser.browser.workspace

import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.properties.Position
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.properties.Size
import com.pashkobohdan.multiwindowbrowser.browser.browsing.HistoryManager

abstract class EqualPartsBrowserSpace() : BrowserSpace() {

    override fun canChangePieceSize() = true
    override fun canChangePiecePosition() = true

    override fun createPiece() {
        val webView = uiCreator.createPieceView()
        val newPiece = BrowserPiece(webView, HistoryManager(), Size(0.0, 0.0), Position(0.0, 0.0))
        add(newPiece)
    }
}