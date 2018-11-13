package com.pashkobohdan.multiwindowbrowser.ui.pieceCreator

import android.content.Context
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.pashkobohdan.multiwindowbrowser.MultiwindowBrowserApplication
import com.pashkobohdan.multiwindowbrowser.R
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.utils.getValidUrlOrGoogleSearch
import com.pashkobohdan.multiwindowbrowser.ui.browserUiCreator.piece.BrowserPieceView
import com.pashkobohdan.multiwindowbrowser.ui.pieceCreator.webClient.DefaultWebClient
import java.io.Serializable
import javax.inject.Inject
import javax.inject.Provider

abstract class UICreator : Serializable {

    @Inject
    lateinit var defaultWebClientProvider: Provider<DefaultWebClient>
    @Inject
    lateinit var contextProvider: Provider<Context>

    private val pieceUiMap = mutableMapOf<BrowserPiece, BrowserPieceView>()

    var rootView: ViewGroup?=null

    var pageCompletedCallback: (String)-> Unit = {}
    var navigatedToUrlCallback: (BrowserPiece, String)-> Unit = { _, _ -> }
    var goToUrlOrSearchCallback: (BrowserPiece, String)-> Unit = { _, _ -> }

    protected val layoutInflater: LayoutInflater by lazy {
        return@lazy LayoutInflater.from(contextProvider.get())
    }

    init {
        MultiwindowBrowserApplication.applicationComponent.inject(this)
    }

    abstract fun inflateRootView(): View

    abstract fun getSpaceSize(): Point

    fun clean(){
        pieceUiMap.clear()
        rootView = null
    }

    fun createViewForPiece(browserPiece: BrowserPiece): BrowserPieceView {
        val pieceView = layoutInflater.inflate(R.layout.web_view_part, null)
        if (pieceView is BrowserPieceView) {
            pieceUiMap.put(browserPiece, pieceView)

            // Init one piece listeners
            pieceView.navigatedToUrlCallback = {
                navigatedToUrlCallback(browserPiece, it)
            }
            pieceView.goToNewUrlOrSearchCallback = {
                goToUrlOrSearchCallback(browserPiece, it)
            }
            pieceView.pageLoadingCompletedCallback = pageCompletedCallback
            //

            rootView?.addView(pieceView)
            return pieceView
        } else {
            throw IllegalStateException("There's no BrowserPieceView in this layout")
        }
    }

    fun removePiece(browserPiece: BrowserPiece) {
        val view = pieceUiMap.get(browserPiece)
        rootView?.removeView(view)
        pieceUiMap.remove(browserPiece)
    }

    fun goToRootPage(browserPiece: BrowserPiece) {
        val pieceWebView = pieceUiMap[browserPiece]
                ?:throw IllegalStateException("Map doesn't contain ${browserPiece}")
        val url = browserPiece.historyManager.currentPage().url.getValidUrlOrGoogleSearch()
        pieceWebView.loadUrl(url)
    }

    fun refreshPieceSize(browserPiece: BrowserPiece) {
        val pieceWebView = pieceUiMap.get(browserPiece)
                    ?: throw IllegalStateException("Map doesn't contain ${browserPiece}")

        val allSpaceSize = getSpaceSize()
        val width = (allSpaceSize.x * browserPiece.size.width).toInt()
        val height = (allSpaceSize.y * browserPiece.size.height).toInt()
        pieceWebView.layoutParams = LinearLayout.LayoutParams(width, height);
    }

    fun goToUrl(browserPiece: BrowserPiece, url: String) {
        val pieceWebView = pieceUiMap[browserPiece]
        if (pieceWebView == null) {
            throw IllegalStateException("Web view for this piece isn't exist")
        }
        pieceWebView.loadUrl(url)
    }
}