package com.pashkobohdan.multiwindowbrowser.browser.workspace.impl

import android.content.Context
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.uiCreator.UICreator
import com.pashkobohdan.multiwindowbrowser.browser.uiCreator.equalsPart.impl.VerticalEqualPartsLinearUICreator
import com.pashkobohdan.multiwindowbrowser.browser.workspace.EqualPartsBrowserSpace
import com.pashkobohdan.multiwindowbrowser.ui.handlers.DialogUiHandler

class VerticalPartsBrowserSpace() : EqualPartsBrowserSpace() {

    override fun newPieceAdded(browserPiece: BrowserPiece) {
        val onePieceHeight = 1.0 / browserPieces.size

        fun pieceXOffset(browserPiece: BrowserPiece): Double {
            return browserPieces.indexOf(browserPiece) * onePieceHeight
        }

        browserPieces.forEach {
            it.apply {
                size.height = onePieceHeight
                position.startX = pieceXOffset(it)
            }
        }
        browserPiece.apply {
            size.height = onePieceHeight
            size.width = 1.0
            position.startY = pieceXOffset(this)
            position.startX = 0.0
        }
    }

    override fun initAndGetUICreator(context: Context, dialogUiHandler: DialogUiHandler): UICreator {
        return VerticalEqualPartsLinearUICreator(context, dialogUiHandler)
    }
}