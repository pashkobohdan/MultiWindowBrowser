package com.pashkobohdan.multiwindowbrowser.browser.browserSpace

abstract class EqualPartsBrowserSpace : BrowserSpace() {

    override fun canChangePieceSize() = true
    override fun canChangePiecePosition() = true
}