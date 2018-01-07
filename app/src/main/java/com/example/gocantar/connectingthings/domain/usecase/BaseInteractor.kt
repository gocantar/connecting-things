package com.example.gocantar.connectingthings.domain.usecase

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by gocantar on 16/11/17.
 */
abstract class BaseInteractor<T, in Param>{

    private val mDisposable: CompositeDisposable = CompositeDisposable()

    abstract fun buildUseCase(params: Param): Observable<T>

    fun execute(disposable: DisposableObserver<T>, params: Param){

        val observable = this.buildUseCase(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        mDisposable.add( observable.subscribeWith(disposable) )
    }

    fun dispose() {
        if (!mDisposable.isDisposed)
            mDisposable.dispose()
    }

}