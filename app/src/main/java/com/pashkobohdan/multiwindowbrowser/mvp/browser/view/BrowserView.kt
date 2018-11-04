package com.pashkobohdan.multiwindowbrowser.mvp.browser.view

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.BrowserSpace
import com.pashkobohdan.multiwindowbrowser.mvp.common.AbstractScreenView

@StateStrategyType(AddToEndSingleStrategy::class)
interface BrowserView : AbstractScreenView {

//    @StateStrategyType(SkipStrategy::class)
    fun initUiCreator(browserSpace: BrowserSpace)

//    @StateStrategyType(SkipStrategy::class)
//    fun addPiece(browserPiece: BrowserPiece)

    @StateStrategyType(SkipStrategy::class)
    fun removePiece(browserPiece: BrowserPiece)

    fun refreshPieces(pieceList: Set<BrowserPiece>)

    fun goToUrl(piece: BrowserPiece, url: String)

}