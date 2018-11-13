package com.pashkobohdan.multiwindowbrowser.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

class TouchableFrameLayout : FrameLayout {

    var onInterceptTouchListener : (MotionEvent) ->Unit = {}

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { onInterceptTouchListener(it) }
        return false
    }
}