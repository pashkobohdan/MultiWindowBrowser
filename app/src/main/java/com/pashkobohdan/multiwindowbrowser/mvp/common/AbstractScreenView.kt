package com.pashkobohdan.multiwindowbrowser.mvp.common

import com.arellomobile.mvp.MvpView

interface AbstractScreenView : MvpView {

    fun onPresenterAttached(presenter: AbstractPresenter<*>)
}
