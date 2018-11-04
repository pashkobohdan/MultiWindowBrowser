package com.pashkobohdan.multiwindowbrowser.ui.common.activity

import com.arellomobile.mvp.MvpAppCompatActivity
import com.pashkobohdan.multiwindowbrowser.mvp.common.AbstractPresenter
import com.pashkobohdan.multiwindowbrowser.mvp.common.AbstractScreenView
import javax.inject.Inject
import javax.inject.Provider

abstract class AbstractScreenActivity<T : AbstractPresenter<*>> : MvpAppCompatActivity(), AbstractScreenView {

    @Inject
    lateinit var presenterProvider: Provider<T>

    protected lateinit var abstractPresenter: AbstractPresenter<*>

    override fun onPresenterAttached(presenter: AbstractPresenter<*>) {
        abstractPresenter = presenter
    }
}