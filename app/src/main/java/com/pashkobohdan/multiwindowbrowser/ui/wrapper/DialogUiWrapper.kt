package com.pashkobohdan.multiwindowbrowser.ui.wrapper

import android.app.Activity
import android.content.Context

class DialogUiWrapper(val activity: Activity) {

    fun getWindow() = activity.window

    fun getRequestedOrientation() = activity.requestedOrientation

    fun setRequestedOrientation(orientation: Int) {
        activity.requestedOrientation = orientation
    }

    fun getContext(): Context = activity
}