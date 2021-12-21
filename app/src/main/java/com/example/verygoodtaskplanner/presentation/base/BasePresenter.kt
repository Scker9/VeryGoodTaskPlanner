package com.example.randomdog.presentation.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import org.koin.core.component.KoinComponent

abstract class BasePresenter<V : BaseView> : MvpPresenter<V>(), KoinComponent {

    private val compositeDisposable = CompositeDisposable()
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    protected fun Disposable.addToCompositeDisposable() {
        compositeDisposable.add(this)
    }
}