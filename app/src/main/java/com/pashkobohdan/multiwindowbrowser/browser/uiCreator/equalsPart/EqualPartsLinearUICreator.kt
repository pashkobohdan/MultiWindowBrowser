package com.pashkobohdan.multiwindowbrowser.browser.uiCreator.equalsPart

import android.content.Context
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.widget.LinearLayout
import com.pashkobohdan.multiwindowbrowser.R
import com.pashkobohdan.multiwindowbrowser.browser.uiCreator.UICreator
import com.pashkobohdan.multiwindowbrowser.ui.handlers.DialogUiHandler

abstract class EqualPartsLinearUICreator(val context: Context, val dialogUiHandler: DialogUiHandler) : UICreator {

    lateinit var layout: LinearLayout
    lateinit var rootView: LinearLayout

    private val layoutInflater: LayoutInflater by lazy {
        return@lazy LayoutInflater.from(context)
    }

    override fun createSpaceAndCall(createdCallback: (View) -> Unit) {

        val createdRootView = layoutInflater.inflate(R.layout.equal_parts_space, null)
        createdCallback(createdRootView)

        if (createdRootView is LinearLayout) {
            createdRootView.orientation = getOrientation()
            this.rootView = createdRootView
        }

        //TODO inflate
        //TODO set orientation
    }

    override fun createPieceView(): WebView {
        val pieceView = layoutInflater.inflate(R.layout.web_view_part, null)
        if (pieceView is WebView) {
            initWebView(pieceView, dialogUiHandler)
            rootView.addView(pieceView)
            return pieceView
        } else {
            throw IllegalStateException("There's no WebView in this layout")
        }
    }

    override fun refreshSpace() {
    }

    abstract fun getOrientation(): Int

    override fun getSpaceSize() = Point(rootView.measuredWidth, rootView.measuredHeight)
}