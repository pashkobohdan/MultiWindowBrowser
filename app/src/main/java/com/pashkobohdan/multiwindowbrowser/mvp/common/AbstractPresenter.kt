package com.pashkobohdan.multiwindowbrowser.mvp.common

import com.arellomobile.mvp.MvpPresenter

open class AbstractPresenter<T : AbstractScreenView> : MvpPresenter<T>() {

    override fun attachView(view: T) {
        super.attachView(view)
        view.onPresenterAttached(this)
    }
}
