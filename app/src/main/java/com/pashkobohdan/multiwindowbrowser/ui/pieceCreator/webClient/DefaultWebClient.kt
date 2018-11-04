package com.pashkobohdan.multiwindowbrowser.ui.pieceCreator.webClient

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.webkit.WebChromeClient
import android.widget.FrameLayout
import com.pashkobohdan.multiwindowbrowser.ui.wrapper.DialogUiWrapper
import javax.inject.Inject

class DefaultWebClient @Inject constructor() : WebChromeClient() {

    @Inject
    lateinit var dialogUiWrapper: DialogUiWrapper

    private var mCustomView: View? = null
    private var mCustomViewCallback: WebChromeClient.CustomViewCallback? = null
    protected var mFullscreenContainer: FrameLayout? = null
    private var mOriginalOrientation: Int = 0
    private var mOriginalSystemUiVisibility: Int = 0

    override fun getDefaultVideoPoster(): Bitmap? {
        return if (this == null) { //TODO !!!
            null
        } else BitmapFactory.decodeResource(dialogUiWrapper.getContext().applicationContext.resources, 2130837573) // TODO wtf ?
    }

    override fun onHideCustomView() {
        (dialogUiWrapper.getWindow().decorView as FrameLayout).removeView(this.mCustomView)
        this.mCustomView = null
        dialogUiWrapper.getWindow().decorView.systemUiVisibility = this.mOriginalSystemUiVisibility
        dialogUiWrapper.setRequestedOrientation(this.mOriginalOrientation)
        this.mCustomViewCallback!!.onCustomViewHidden()
        this.mCustomViewCallback = null
    }

    override fun onShowCustomView(paramView: View, paramCustomViewCallback: WebChromeClient.CustomViewCallback) {
        if (this.mCustomView != null) {
            onHideCustomView()
            return
        }
        this.mCustomView = paramView
        this.mOriginalSystemUiVisibility = dialogUiWrapper.getWindow().decorView.systemUiVisibility
        this.mOriginalOrientation = dialogUiWrapper.getRequestedOrientation()
        this.mCustomViewCallback = paramCustomViewCallback
        (dialogUiWrapper.getWindow().decorView as FrameLayout).addView(this.mCustomView, FrameLayout.LayoutParams(-1, -1))
        dialogUiWrapper.getWindow().decorView.systemUiVisibility = 3846
    }
}