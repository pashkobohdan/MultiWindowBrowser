package com.pashkobohdan.multiwindowbrowser.ui.browserUiCreator

import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.BrowserSpace
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.impl.HorizontalPartsBrowserSpace
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.impl.VerticalPartsBrowserSpace
import com.pashkobohdan.multiwindowbrowser.ui.pieceCreator.UICreator
import com.pashkobohdan.multiwindowbrowser.ui.pieceCreator.equalsPart.impl.HorizontalEqualPartsLinearUICreator
import com.pashkobohdan.multiwindowbrowser.ui.pieceCreator.equalsPart.impl.VerticalEqualPartsLinearUICreator

object BrowserUiCreatorFactory {

    fun createUiCreator(space: BrowserSpace): UICreator {
        return when (space) {
            is HorizontalPartsBrowserSpace -> HorizontalEqualPartsLinearUICreator()
            is VerticalPartsBrowserSpace -> VerticalEqualPartsLinearUICreator()
            else -> throw IllegalArgumentException("Unsupported space type: " + space::class)
        }
    }
}