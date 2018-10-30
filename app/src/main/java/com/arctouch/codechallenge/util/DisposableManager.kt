package com.arctouch.codechallenge.util

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

object DisposableManager {
    var compositeDisposable: CompositeDisposable? = null
        get() = if (field == null || field!!.isDisposed) {
            CompositeDisposable()
        } else {
            field
        }

    fun add(disposable: Disposable) {
        compositeDisposable?.add(disposable)
    }

    fun dispose() {
        compositeDisposable?.dispose()
    }

    fun clear(){
        compositeDisposable?.clear()
    }

}