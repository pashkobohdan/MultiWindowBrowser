package com.pashkobohdan.multiwindowbrowser.ui.activities

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.pashkobohdan.multiwindowbrowser.MultiwindowBrowserApplication
import com.pashkobohdan.multiwindowbrowser.R
import com.pashkobohdan.multiwindowbrowser.browser.workspace.impl.HorizontalPartsBrowserSpace
import com.pashkobohdan.multiwindowbrowser.mvp.browser.BrowserPresenter
import com.pashkobohdan.multiwindowbrowser.mvp.browser.view.BrowserView
import com.pashkobohdan.multiwindowbrowser.ui.activities.common.AbstractScreenActivity

class BrowserActivity : AbstractScreenActivity<BrowserPresenter>(), BrowserView {

    @InjectPresenter
    lateinit var presenter: BrowserPresenter

    @ProvidePresenter
    fun providePresenter(): BrowserPresenter {
        return presenterProvider.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiwindowBrowserApplication.applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val space1 = HorizontalPartsBrowserSpace()
//        space1.createSpace(this, this) {view ->
//            browser_space_root.addView(view)
//
//            view.doOnceAfterCreate {
//                space1.createPiece()
//                space1.createPiece()
//                space1.createPiece()
//            }
//        }
    }

    internal inner class Browser : WebViewClient() {

        override fun shouldOverrideUrlLoading(paramWebView: WebView, paramString: String): Boolean {
            paramWebView.loadUrl(paramString)
            return true
        }
    }


//    inner class MyWebClient : WebChromeClient() {
//        private var mCustomView: View? = null
//        private var mCustomViewCallback: WebChromeClient.CustomViewCallback? = null
//        protected var mFullscreenContainer: FrameLayout? = null
//        private var mOriginalOrientation: Int = 0
//        private var mOriginalSystemUiVisibility: Int = 0
//
//        override fun getDefaultVideoPoster(): Bitmap? {
//            return if (this@BrowserActivity == null) {
//                null
//            } else BitmapFactory.decodeResource(this@BrowserActivity.applicationContext.resources, 2130837573)
//        }
//
//        override fun onHideCustomView() {
//            (this@BrowserActivity.window.decorView as FrameLayout).removeView(this.mCustomView)
//            this.mCustomView = null
//            this@BrowserActivity.window.decorView.systemUiVisibility = this.mOriginalSystemUiVisibility
//            this@BrowserActivity.requestedOrientation = this.mOriginalOrientation
//            this.mCustomViewCallback!!.onCustomViewHidden()
//            this.mCustomViewCallback = null
//        }
//
//        override fun onShowCustomView(paramView: View, paramCustomViewCallback: WebChromeClient.CustomViewCallback) {
//            if (this.mCustomView != null) {
//                onHideCustomView()
//                return
//            }
//            this.mCustomView = paramView
//            this.mOriginalSystemUiVisibility = this@BrowserActivity.window.decorView.systemUiVisibility
//            this.mOriginalOrientation = this@BrowserActivity.requestedOrientation
//            this.mCustomViewCallback = paramCustomViewCallback
//            (this@BrowserActivity.window.decorView as FrameLayout).addView(this.mCustomView, FrameLayout.LayoutParams(-1, -1))
//            this@BrowserActivity.window.decorView.systemUiVisibility = 3846
//        }
//    }
}
