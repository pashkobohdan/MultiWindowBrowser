package com.pashkobohdan.multiwindowbrowser.interactor.common

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver


abstract class GetUseCase<T> : AbstractUseCase<T>() {

    protected abstract fun getData(): T

    fun execute(subscriber: DisposableObserver<T>): Disposable {
        return execute(Observable.fromCallable(this::getData), subscriber)
    }
}