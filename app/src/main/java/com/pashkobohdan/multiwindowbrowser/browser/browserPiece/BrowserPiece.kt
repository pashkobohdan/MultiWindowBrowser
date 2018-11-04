package com.pashkobohdan.multiwindowbrowser.browser.browserPiece

import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.properties.Position
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.properties.Size
import com.pashkobohdan.multiwindowbrowser.browser.browsing.HistoryManager


class BrowserPiece(val historyManager: HistoryManager,
                   val size: Size,
                   val position: Position) {
}