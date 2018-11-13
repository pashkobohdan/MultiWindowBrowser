package com.pashkobohdan.multiwindowbrowser.mvp.browser.view

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.BrowserSpace
import com.pashkobohdan.multiwindowbrowser.mvp.common.AbstractScreenView

@StateStrategyType(SkipStrategy::class)//TODO back some of them
interface BrowserView : AbstractScreenView {

//    @StateStrategyType(SkipStrategy::class)
    fun initUiCreator(browserSpace: BrowserSpace)

//    @StateStrategyType(SkipStrategy::class)
//    fun addPiece(browserPiece: BrowserPiece)

    fun removePiece(browserPiece: BrowserPiece)

    fun addPiece(browserPiece: BrowserPiece)

    fun goToUrl(piece: BrowserPiece, url: String)

    fun makePrintScreenForSave()

}