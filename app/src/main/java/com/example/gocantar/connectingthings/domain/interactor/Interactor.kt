package com.example.gocantar.connectingthings.domain.interactor

import io.reactivex.observers.DisposableObserver

/**
 * Created by gocantar on 2/11/17.
 */
interface Interactor<T> {
    fun execute(disposable: DisposableObserver<T>)
    fun dispose()
}