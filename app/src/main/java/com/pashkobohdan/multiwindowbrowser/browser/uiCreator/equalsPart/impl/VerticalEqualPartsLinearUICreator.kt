package com.pashkobohdan.multiwindowbrowser.browser.uiCreator.equalsPart.impl

import android.content.Context
import android.widget.LinearLayout
import com.pashkobohdan.multiwindowbrowser.browser.uiCreator.equalsPart.EqualPartsLinearUICreator
import com.pashkobohdan.multiwindowbrowser.ui.handlers.DialogUiHandler

class VerticalEqualPartsLinearUICreator(context: Context, dialogUiHandler: DialogUiHandler)
    : EqualPartsLinearUICreator(context, dialogUiHandler) {

    override fun getOrientation() = LinearLayout.VERTICAL
}