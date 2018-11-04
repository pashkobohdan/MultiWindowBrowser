package com.pashkobohdan.multiwindowbrowser.interactor.common.observers

abstract class UnitObserver : DefaultObserver<Unit>() {

    override fun onNext(t: Unit) = onNext()

    abstract fun onNext()
}