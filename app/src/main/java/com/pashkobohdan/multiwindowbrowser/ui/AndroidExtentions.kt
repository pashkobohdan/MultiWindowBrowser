package com.pashkobohdan.multiwindowbrowser.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.pashkobohdan.multiwindowbrowser.ui.browserUiCreator.piece.BrowserPieceView
import com.pashkobohdan.multiwindowbrowser.ui.listeners.OneTimeOnGlobalLayoutListener


fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus?:View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.hideKeyboardFrom(context: Context) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.hideKeyboardFrom() = hideKeyboardFrom(context)

fun View.doOnceAfterCreate(toDo: ()->Unit) {
    this.viewTreeObserver.addOnGlobalLayoutListener(OneTimeOnGlobalLayoutListener(this, toDo))
}

const val MIN_THREE_FINGERS_TAP_DELAY_MS = 200L

fun View.doIfSeveralFingersTouch(count: Int, toDo: () -> Unit, minTapsDelay : Long = MIN_THREE_FINGERS_TAP_DELAY_MS) {
    setOnTouchListener (object: View.OnTouchListener {
        var lastClickTime = 0L
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            if(event?.pointerCount == 3) {
                val clickTime = System.currentTimeMillis()
                if(lastClickTime == 0L || clickTime - lastClickTime > BrowserPieceView.MIN_THREE_FINGERS_TAP_DELAY_MS) {
                    lastClickTime = clickTime
                    toDo()
                }
            }
            return false
        }
    })
}