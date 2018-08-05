package com.pashkobohdan.multiwindowbrowser.mvp.browser.view

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.workspace.BrowserSpace
import com.pashkobohdan.multiwindowbrowser.mvp.common.AbstractScreenView

@StateStrategyType(AddToEndSingleStrategy::class)
interface BrowserView : AbstractScreenView {

    fun initSpace(browserSpace: BrowserSpace)

    fun addPiece(browserPiece: BrowserPiece)

    fun removePiece(browserPiece: BrowserPiece)
}