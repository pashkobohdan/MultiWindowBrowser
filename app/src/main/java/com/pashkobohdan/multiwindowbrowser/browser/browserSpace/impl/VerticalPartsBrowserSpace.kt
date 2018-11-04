package com.pashkobohdan.multiwindowbrowser.browser.browserSpace.impl

import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.EqualPartsBrowserSpace

class VerticalPartsBrowserSpace : EqualPartsBrowserSpace() {

    override fun initNewAddedPiece(newPiece: BrowserPiece) {
        val onePieceHeight = 1.0 / browserPieces.size

        fun pieceXOffset(browserPiece: BrowserPiece): Double {
            return browserPieces.indexOf(browserPiece) * onePieceHeight
        }

        //TODO delete me ???
        browserPieces.forEach {
            it.apply {
                size.height = onePieceHeight
                position.startX = pieceXOffset(it)
            }
        }
        newPiece.apply {
            size.height = onePieceHeight
            size.width = 1.0
            position.startY = pieceXOffset(this)
            position.startX = 0.0
        }
    }

    override fun reInitPiecesAfterRemoving() {
        val onePieceHeight = 1.0 / browserPieces.size

        fun pieceXOffset(browserPiece: BrowserPiece): Double {
            return browserPieces.indexOf(browserPiece) * onePieceHeight
        }

        //TODO delete me ???
        browserPieces.forEach {
            it.apply {
                size.height = onePieceHeight
                position.startX = pieceXOffset(it)
            }
        }
    }
}