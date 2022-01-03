package com.example.randomdog.presentation.base

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface BaseView : MvpView {
    fun onError(errorMessage: String) {

    }

    fun onSuccess(message: String) {

    }
    fun onSuccess() {

    }
}