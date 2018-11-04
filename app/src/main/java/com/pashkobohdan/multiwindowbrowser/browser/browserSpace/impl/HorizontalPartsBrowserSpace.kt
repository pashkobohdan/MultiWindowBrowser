package com.pashkobohdan.multiwindowbrowser.browser.browserSpace.impl

import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.EqualPartsBrowserSpace

class HorizontalPartsBrowserSpace : EqualPartsBrowserSpace() {

    override fun initNewAddedPiece(newPiece: BrowserPiece) {
        val onePieceWidth = 1.0 / browserPieces.size

        fun pieceXOffset(browserPiece: BrowserPiece): Double {
            return browserPieces.indexOf(browserPiece) * onePieceWidth
        }

        //TODO remove ???
        browserPieces.forEach {
            it.apply {
                size.width = onePieceWidth
                position.startX = pieceXOffset(it)
            }
        }
        newPiece.apply {
            size.width = onePieceWidth
            size.height = 1.0
            position.startX = pieceXOffset(this)
            position.startY = 0.0
        }
    }

    override fun reInitPiecesAfterRemoving() {
        val onePieceWidth = 1.0 / browserPieces.size

        fun pieceXOffset(browserPiece: BrowserPiece): Double {
            return browserPieces.indexOf(browserPiece) * onePieceWidth
        }

        //TODO remove ???
        browserPieces.forEach {
            it.apply {
                size.width = onePieceWidth
                position.startX = pieceXOffset(it)
            }
        }
    }
}