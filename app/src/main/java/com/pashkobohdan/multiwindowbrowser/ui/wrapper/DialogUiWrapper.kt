package com.pashkobohdan.multiwindowbrowser.ui.wrapper

import android.app.Activity

class DialogUiWrapper(val activity: Activity) {

    fun getWindow() = activity.window

    fun getRequestedOrientation() = activity.requestedOrientation

    fun setRequestedOrientation(orientation: Int) {
        activity.requestedOrientation = orientation
    }
}