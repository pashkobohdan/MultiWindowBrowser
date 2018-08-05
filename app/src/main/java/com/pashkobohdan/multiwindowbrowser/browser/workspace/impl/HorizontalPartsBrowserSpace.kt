package com.pashkobohdan.multiwindowbrowser.browser.workspace.impl

import android.content.Context
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.uiCreator.UICreator
import com.pashkobohdan.multiwindowbrowser.browser.uiCreator.equalsPart.impl.HorizontalEqualPartsLinearUICreator
import com.pashkobohdan.multiwindowbrowser.browser.workspace.EqualPartsBrowserSpace
import com.pashkobohdan.multiwindowbrowser.ui.handlers.DialogUiHandler

class HorizontalPartsBrowserSpace : EqualPartsBrowserSpace() {

    override fun newPieceAdded(browserPiece: BrowserPiece) {
        val onePieceWidth = 1.0 / browserPieces.size

        fun pieceXOffset(browserPiece: BrowserPiece): Double {
            return browserPieces.indexOf(browserPiece) * onePieceWidth
        }

        browserPieces.forEach {
            it.apply {
                size.width = onePieceWidth
                position.startX = pieceXOffset(it)
            }
        }
        browserPiece.apply {
            size.width = onePieceWidth
            size.height = 1.0
            position.startX = pieceXOffset(this)
            position.startY = 0.0
        }
    }

    override fun initAndGetUICreator(context: Context, dialogUiHandler: DialogUiHandler): UICreator {
        return HorizontalEqualPartsLinearUICreator(context, dialogUiHandler)
    }
}