package com.pashkobohdan.multiwindowbrowser.ui.fragments.spacePieces

import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.BrowserSpace

interface SpacePiecesUIHandler {
    var browserSpace: BrowserSpace?

    fun removePiece(browserPiece: BrowserPiece)

    fun addPiece(browserPiece: BrowserPiece)

    fun goToNewUrl(piece: BrowserPiece, url: String)

    fun setPageCompletedCallback(toDo: (String) -> Unit)

    fun setNavigatedToNewUrlCallback(toDo: (BrowserPiece, String) -> Unit)

    fun setGoToUrlOrSearchCallback1(toDo: (BrowserPiece, String) -> Unit)
}