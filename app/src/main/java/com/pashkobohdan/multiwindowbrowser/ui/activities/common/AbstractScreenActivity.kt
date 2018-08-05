package com.pashkobohdan.multiwindowbrowser.ui.activities.common

import android.support.v7.app.AppCompatActivity
import com.pashkobohdan.multiwindowbrowser.mvp.common.AbstractPresenter
import com.pashkobohdan.multiwindowbrowser.mvp.common.AbstractScreenView
import javax.inject.Inject
import javax.inject.Provider

abstract class AbstractScreenActivity<T : AbstractPresenter<*>> : AppCompatActivity(), AbstractScreenView {

    @Inject
    lateinit var presenterProvider: Provider<T>

    protected lateinit var abstractPresenter: AbstractPresenter<*>

    override fun onPresenterAttached(presenter: AbstractPresenter<*>) {
        abstractPresenter = presenter
    }
}