package com.pashkobohdan.multiwindowbrowser.browser.workspace

import android.content.Context
import android.view.View
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.uiCreator.UICreator
import com.pashkobohdan.multiwindowbrowser.ui.handlers.DialogUiHandler

abstract class BrowserSpace() {

    internal val browserPieces = mutableSetOf<BrowserPiece>()
    lateinit var uiCreator: UICreator

    fun createSpace(context: Context, dialogUiHandler: DialogUiHandler, viewInitCallback: (View) -> Unit) {
        uiCreator = initAndGetUICreator(context,dialogUiHandler)
        uiCreator.createSpaceAndCall(viewInitCallback)
    }

    fun cleanSpace() {
        browserPieces.clear()
    }

    abstract fun createPiece()

    internal fun add(browserPiece: BrowserPiece) {
        browserPieces.add(browserPiece)
        newPieceAdded(browserPiece)
        browserPieces.forEach {
            it.refresh(uiCreator.getSpaceSize())
        }
    }

    abstract fun newPieceAdded(browserPiece: BrowserPiece)

    abstract fun canChangePieceSize(): Boolean

    abstract fun canChangePiecePosition(): Boolean

    abstract fun initAndGetUICreator(context: Context, dialogUiHandler: DialogUiHandler): UICreator

    //TODO resize method, etc
}