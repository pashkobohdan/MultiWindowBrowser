package com.pashkobohdan.multiwindowbrowser

import android.os.Build
import android.webkit.*


class WebViewClientImpl (val goToUrl: (String)->Unit, val pageLoadingFinishedFinished :(String)->Unit): WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToUrl(request?.url.toString())
        }
        return false
    }

    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
        //TODO check for YOUTUBE and run goToUrl(...)
        return super.shouldInterceptRequest(view, url)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        url?.let {pageLoadingFinishedFinished(it)}
    }

    override fun onReceivedHttpAuthRequest(view: WebView?, handler: HttpAuthHandler?, host: String?, realm: String?) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm)
        println("onReceivedHttpAuthRequest $host")
    }

    override fun onReceivedLoginRequest(view: WebView?, realm: String?, account: String?, args: String?) {
        super.onReceivedLoginRequest(view, realm, account, args)
        println("onReceivedLoginRequest $account")
    }
}