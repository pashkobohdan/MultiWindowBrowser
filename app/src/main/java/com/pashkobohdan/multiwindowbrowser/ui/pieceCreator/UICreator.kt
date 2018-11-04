package com.pashkobohdan.multiwindowbrowser.ui.pieceCreator

import android.content.Context
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.LinearLayout
import com.pashkobohdan.multiwindowbrowser.MultiwindowBrowserApplication
import com.pashkobohdan.multiwindowbrowser.R
import com.pashkobohdan.multiwindowbrowser.WebViewClientImpl
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.browsing.Page
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

    lateinit var rootView: ViewGroup

    var historyChangedCallback: ()-> Unit = {}
    var pageLoadedCallback: ()-> Unit = {}
    var goToPageCallback: (BrowserPiece, String)-> Unit = {_, _ -> }

    protected val layoutInflater: LayoutInflater by lazy {
        return@lazy LayoutInflater.from(contextProvider.get())
    }

    init {
        MultiwindowBrowserApplication.applicationComponent.inject(this)
    }

    abstract fun inflateRootView(): View

    abstract fun refreshSpace()

    abstract fun getSpaceSize(): Point

    private fun createViewForPiece(browserPiece: BrowserPiece): BrowserPieceView {
        val pieceView = layoutInflater.inflate(R.layout.web_view_part, null)
        if (pieceView is BrowserPieceView) {
            pieceUiMap.put(browserPiece, pieceView)
            initWebView(pieceView, browserPiece)
            rootView.addView(pieceView)
            return pieceView
        } else {
            throw IllegalStateException("There's no BrowserPieceView in this layout")
        }
    }

    private fun goToRootPage(browserPiece: BrowserPiece) {
        val pieceWebView = pieceUiMap[browserPiece]
        if (pieceWebView == null) {
            throw IllegalStateException("Web view for this piece isn't exist")
        }
        val url = browserPiece.historyManager.currentPage().url.getValidUrlOrGoogleSearch()
        pieceWebView.loadUrl(url)
        pieceWebView.setCurrentUrl(url)
    }

    private fun initWebView(view: BrowserPieceView, browserPiece: BrowserPiece) {
        val webViewClient = WebViewClientImpl( {newUrl->
            browserPiece.historyManager.goToPage(Page(newUrl))
            historyChangedCallback()
            view.setCurrentUrl(newUrl)
        }, {
            pageLoadedCallback()
        })

        view.goToUrlCallback = {
            goToPageCallback(browserPiece, it)
        }

        view.webView.getSettings().setPluginState(WebSettings.PluginState.ON)
        view.webView.getSettings().setJavaScriptEnabled(true)
        view.webView.getSettings().setAppCacheEnabled(true)
        view.webView.setInitialScale(1)
        view.webView.getSettings().setLoadWithOverviewMode(true)
        view.webView.getSettings().setUseWideViewPort(true)

        view.webView.webViewClient = webViewClient
        view.webView.webChromeClient = defaultWebClientProvider.get()
    }

    fun tryRefreshPieceSize(browserPiece: BrowserPiece) {
        val pieceWebView: BrowserPieceView
        if ( pieceUiMap.contains(browserPiece)) {
            pieceWebView = pieceUiMap.get(browserPiece)
                    ?: throw IllegalStateException("Map doesn't contain ${browserPiece}")
        } else {
            pieceWebView = createViewForPiece(browserPiece)
            goToRootPage(browserPiece)
        }
//        if (pieceWebView == null) {
//            throw IllegalStateException("Web view for this piece isn't exist")
//        }

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
        pieceWebView.setCurrentUrl(url)
    }
}