package com.pashkobohdan.multiwindowbrowser.browser.uiCreator

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.webkit.WebChromeClient
import android.widget.FrameLayout
import com.pashkobohdan.multiwindowbrowser.ui.handlers.DialogUiHandler

class DefaultWebClient(val context: Context, val dialogUiHandler: DialogUiHandler) : WebChromeClient() {
    private var mCustomView: View? = null
    private var mCustomViewCallback: WebChromeClient.CustomViewCallback? = null
    protected var mFullscreenContainer: FrameLayout? = null
    private var mOriginalOrientation: Int = 0
    private var mOriginalSystemUiVisibility: Int = 0

    override fun getDefaultVideoPoster(): Bitmap? {
        return if (this == null) {
            null
        } else BitmapFactory.decodeResource(context.applicationContext.resources, 2130837573)
    }

    override fun onHideCustomView() {
        (dialogUiHandler.getWindow().decorView as FrameLayout).removeView(this.mCustomView)
        this.mCustomView = null
        dialogUiHandler.getWindow().decorView.systemUiVisibility = this.mOriginalSystemUiVisibility
        dialogUiHandler.setRequestedOrientation(this.mOriginalOrientation)
        this.mCustomViewCallback!!.onCustomViewHidden()
        this.mCustomViewCallback = null
    }

    override fun onShowCustomView(paramView: View, paramCustomViewCallback: WebChromeClient.CustomViewCallback) {
        if (this.mCustomView != null) {
            onHideCustomView()
            return
        }
        this.mCustomView = paramView
        this.mOriginalSystemUiVisibility = dialogUiHandler.getWindow().decorView.systemUiVisibility
        this.mOriginalOrientation = dialogUiHandler.getRequestedOrientation()
        this.mCustomViewCallback = paramCustomViewCallback
        (dialogUiHandler.getWindow().decorView as FrameLayout).addView(this.mCustomView, FrameLayout.LayoutParams(-1, -1))
        dialogUiHandler.getWindow().decorView.systemUiVisibility = 3846
    }
}