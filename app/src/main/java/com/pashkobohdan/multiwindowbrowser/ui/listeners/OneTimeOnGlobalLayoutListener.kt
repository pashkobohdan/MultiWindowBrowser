package com.pashkobohdan.multiwindowbrowser.ui.listeners

import android.os.Build
import android.view.View
import android.view.ViewTreeObserver

class OneTimeOnGlobalLayoutListener(val view: View, val toDo: ()->Unit) : ViewTreeObserver.OnGlobalLayoutListener {

    private var didOnce: Boolean = false

    override fun onGlobalLayout() {
        if(didOnce == true) {
            didOnce = true
            return
        }
        toDo()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    }
}