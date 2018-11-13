package com.pashkobohdan.multiwindowbrowser.ui.browserUiCreator.piece

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout
import com.pashkobohdan.multiwindowbrowser.R
import com.pashkobohdan.multiwindowbrowser.WebViewClientImpl
import com.pashkobohdan.multiwindowbrowser.ui.doIfSeveralFingersTouch
import com.pashkobohdan.multiwindowbrowser.ui.hideKeyboardFrom
import com.pashkobohdan.multiwindowbrowser.ui.listeners.AnimatorEndListener
import kotlinx.android.synthetic.main.view_web_view_part.view.*

class BrowserPieceView : LinearLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var navigatedToUrlCallback: (String) -> Unit = {}
    var goToNewUrlOrSearchCallback: (String) -> Unit = {}
    var pageLoadingCompletedCallback: (String) -> Unit = {}//TODO add !
    var activatePieceCallback: () -> Unit = {}

    private val view: View = inflate(context, R.layout.view_web_view_part, this)

    var webView: WebView = view.webView

    init {
        view.searchEdit.setOnKeyListener { _, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                goToNewUrlOrSearchCallback(view.searchEdit.text.toString())
                view.searchEdit.hideKeyboardFrom()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false;
        }
        view.touchableRoot.onInterceptTouchListener = { event ->
            activatePieceCallback()
        }

        val webView = view.webView

        webView.doIfSeveralFingersTouch(3, {
            if (headerContainer.visibility == View.VISIBLE) {
                headerContainer
                        .animate()
                        .alpha(0f)
                        .translationYBy(-headerContainer.height.toFloat())
                        .setListener(AnimatorEndListener {
                            headerContainer.visibility = View.GONE
                        })
            } else {
                headerContainer
                        .animate()
                        .alpha(1f)
                        .translationYBy(headerContainer.height.toFloat())
                        .setListener(AnimatorEndListener {
                            headerContainer.visibility = View.VISIBLE
                        })
            }
        })



        webView.settings.pluginState = WebSettings.PluginState.ON
        webView.settings.javaScriptEnabled = true
        webView.settings.setAppCacheEnabled(true)
        webView.setInitialScale(1)
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true

        webView.settings.setAppCacheEnabled(true)
        webView.settings.setSupportMultipleWindows(true)
        webView.settings.javaScriptCanOpenWindowsAutomatically = true


        //Cookie manager for the webview
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)

//        webView.settings.userAgentString = "Chrome"

        webView.webViewClient = WebViewClientImpl({ newUrl ->
            //            browserPiece.historyManager.navigatedToUrl(Page(newUrl))//TODO make sure to do it in presenter
            navigatedToUrlCallback(newUrl)
            setCurrentUrl(newUrl)
        }, pageLoadingCompletedCallback)

        webView.webChromeClient = object : WebChromeClient() {

            var mCustomView: View? = null
            var mCustomViewCallback: WebChromeClient.CustomViewCallback? = null

            override fun onHideCustomView() {
                if (mCustomView != null) {
                    view.fullscreenContentContainer.removeView(mCustomView)
                    view.fullscreenContentContainer.visibility = View.GONE
                    mCustomViewCallback?.onCustomViewHidden();
                    mCustomView = null;
                    contentContainer.visibility = View.VISIBLE
                }
            }


            override fun onShowCustomView(paramView: View, callback: WebChromeClient.CustomViewCallback) {
                if (mCustomView != null) {
                    callback.onCustomViewHidden();
                } else {
                    mCustomView = paramView;
                    mCustomViewCallback = callback;
                    contentContainer.visibility = View.GONE
                    view.fullscreenContentContainer.addView(paramView)
                    view.fullscreenContentContainer.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setCurrentUrl(url: String) {
        view.searchEdit.setText(url)
    }

    fun loadUrl(url: String) {
        setCurrentUrl(url)
        webView.loadUrl(url)
    }

    fun onSaveInstanceState(outState: Bundle) {
        webView.saveState(outState)
        outState.putParcelable(SEARCH_EDIT_TEXT_STATE_KEY, view.searchEdit.onSaveInstanceState())
    }

    fun onViewStateRestored(savedInstanceState: Bundle) {
        webView.restoreState(savedInstanceState)
        (savedInstanceState.getParcelable(SEARCH_EDIT_TEXT_STATE_KEY) as Parcelable?)?.let {
            view.searchEdit.onRestoreInstanceState(it)
        }
    }

    companion object {

        const val MIN_THREE_FINGERS_TAP_DELAY_MS = 200
        const val SEARCH_EDIT_TEXT_STATE_KEY = "searchEditStateKey"
    }
}