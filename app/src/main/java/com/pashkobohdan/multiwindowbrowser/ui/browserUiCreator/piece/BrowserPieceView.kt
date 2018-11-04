package com.pashkobohdan.multiwindowbrowser.ui.browserUiCreator.piece

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.widget.LinearLayout
import com.pashkobohdan.multiwindowbrowser.R
import com.pashkobohdan.multiwindowbrowser.ui.doIfSeveralFingersTouch
import com.pashkobohdan.multiwindowbrowser.ui.hideKeyboardFrom
import com.pashkobohdan.multiwindowbrowser.ui.listeners.AnimatorEndListener
import kotlinx.android.synthetic.main.view_web_view_part.view.*

class BrowserPieceView : LinearLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var goToUrlCallback: (String) -> Unit = {}

    val view: View

    init {
        view = inflate(context, R.layout.view_web_view_part, this)


        view.searchEdit.setOnKeyListener { _, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                goToUrlCallback(view.searchEdit.text.toString())
                view.searchEdit.hideKeyboardFrom()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false;
        }

        view.webView.doIfSeveralFingersTouch(3, {
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
    }

    fun setCurrentUrl(url: String) {
        view.searchEdit.setText(url)
    }

    fun loadUrl(url: String) {
        view.webView.loadUrl(url)
    }

    var webView: WebView = view.webView

    companion object {

        const val MIN_THREE_FINGERS_TAP_DELAY_MS = 200
    }
}