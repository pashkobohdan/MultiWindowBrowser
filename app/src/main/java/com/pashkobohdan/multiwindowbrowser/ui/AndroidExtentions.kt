package com.pashkobohdan.multiwindowbrowser.ui

import android.view.View
import com.pashkobohdan.multiwindowbrowser.ui.listeners.OneTimeOnGlobalLayoutListener

fun View.doOnceAfterCreate(toDo: ()->Unit) {
    this.viewTreeObserver.addOnGlobalLayoutListener(OneTimeOnGlobalLayoutListener(this, toDo))
}