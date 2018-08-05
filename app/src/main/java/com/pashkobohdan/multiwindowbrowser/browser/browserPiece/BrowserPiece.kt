package com.pashkobohdan.multiwindowbrowser.browser.browserPiece

import android.graphics.Point
import android.webkit.WebView
import android.widget.LinearLayout
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.properties.Position
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.properties.Size
import com.pashkobohdan.multiwindowbrowser.browser.browsing.HistoryManager



class BrowserPiece(val webView: WebView,
                   val historyManager: HistoryManager,
                   val size: Size,
                   val position: Position) {

    fun refresh(maxSpaceSize: Point) {
        val width = (maxSpaceSize.x * size.width).toInt()
        val height = (maxSpaceSize.y * size.height).toInt()
        webView.layoutParams = LinearLayout.LayoutParams(width, height);

        webView.loadUrl("http://www.google.com/")
    }
}