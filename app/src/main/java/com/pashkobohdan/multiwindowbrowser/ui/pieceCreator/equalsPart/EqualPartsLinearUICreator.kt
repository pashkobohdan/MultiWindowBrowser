package com.pashkobohdan.multiwindowbrowser.ui.pieceCreator.equalsPart

import android.graphics.Point
import android.view.View
import android.widget.LinearLayout
import com.pashkobohdan.multiwindowbrowser.R
import com.pashkobohdan.multiwindowbrowser.ui.pieceCreator.UICreator

abstract class EqualPartsLinearUICreator : UICreator() {

    lateinit var layout: LinearLayout

    override fun inflateRootView():View {
        val createdRootView = layoutInflater.inflate(R.layout.equal_parts_space, null)

        if (createdRootView is LinearLayout) {
            this.rootView = createdRootView
            createdRootView.orientation = getOrientation()
            return rootView
        } else {
            throw IllegalStateException("Root view ($rootView) isn't linearLayout !")
        }
    }

    override fun refreshSpace() {
    }

    abstract fun getOrientation(): Int

    override fun getSpaceSize() = Point(rootView.measuredWidth, rootView.measuredHeight)
}