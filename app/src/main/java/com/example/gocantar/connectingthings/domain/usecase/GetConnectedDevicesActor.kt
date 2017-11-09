package com.example.gocantar.connectingthings.domain.usecase

import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.domain.interactor.Interactor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by gocantar on 2/11/17.
 */

class GetConnectedDevicesActor @Inject constructor(private val mBLEService: BLEServiceBoundary): Interactor<BLEDevice>{

    private val mDisposables: CompositeDisposable = CompositeDisposable()

    override fun execute(disposable: DisposableObserver<BLEDevice>) {

        val observable = mBLEService.getConnectedDevices().toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        mDisposables.add( observable.subscribeWith(disposable))

    }


    override fun dispose() {
        if (!mDisposables.isDisposed) {
            mDisposables.dispose()
        }
    }



}