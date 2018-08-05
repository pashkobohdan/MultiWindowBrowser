package com.pashkobohdan.multiwindowbrowser.ui.handlers

import android.view.Window

interface DialogUiHandler {

    fun getWindow(): Window

    fun getRequestedOrientation(): Int

    fun setRequestedOrientation(orientation: Int)
}