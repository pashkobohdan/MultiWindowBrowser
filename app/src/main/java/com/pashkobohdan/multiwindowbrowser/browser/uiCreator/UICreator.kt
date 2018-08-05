package com.pashkobohdan.multiwindowbrowser.browser.uiCreator

import android.graphics.Point
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import com.pashkobohdan.multiwindowbrowser.WebViewClientImpl
import com.pashkobohdan.multiwindowbrowser.ui.handlers.DialogUiHandler

interface UICreator {

    fun createSpaceAndCall(createdCallback: (View) -> Unit)

    fun createPieceView(): WebView

    fun refreshSpace()

    fun initWebView(view: WebView, dialogUiHandler: DialogUiHandler) {
        val webViewClient = WebViewClientImpl()

        view.getSettings().setPluginState(WebSettings.PluginState.ON)
        view.setWebChromeClient(WebChromeClient())
        view.getSettings().setJavaScriptEnabled(true)
        view.getSettings().setAppCacheEnabled(true)
        view.setInitialScale(1)
        view.getSettings().setLoadWithOverviewMode(true)
        view.getSettings().setUseWideViewPort(true)
        view.setWebViewClient(webViewClient)

        view.webChromeClient = DefaultWebClient(view.context, dialogUiHandler)
    }

    fun getSpaceSize(): Point
}