package com.pashkobohdan.multiwindowbrowser.mvp.browser

import com.arellomobile.mvp.InjectViewState
import com.pashkobohdan.multiwindowbrowser.mvp.browser.view.BrowserView
import com.pashkobohdan.multiwindowbrowser.mvp.common.AbstractPresenter

@InjectViewState
class BrowserPresenter : AbstractPresenter<BrowserView>(){

    override fun onFirstViewAttach() {
        //TODO
    }

    fun saveSpaceToCurrent() {
        //TODO
    }

    fun saveSpaceToList() {

    }
}