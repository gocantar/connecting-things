package com.example.gocantar.connectingthings.domain.usecase

import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.domain.interactor.ScanDevicesInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by gocantar on 10/10/17.
 */

class ScanDevicesActor @Inject constructor(private val mBLEService: BLEServiceBoundary): ScanDevicesInteractor {

    private val mDisposables: CompositeDisposable = CompositeDisposable()

    override fun start(disposable: DisposableObserver<BLEDevice>){

        mBLEService.start()

        val observable = mBLEService.mPublisherOfBLEDevice
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())

        mDisposables.add(observable.subscribeWith(disposable))

    }

    override fun stop(){
        mBLEService.stop()
    }

    override fun dispose(){
        if (!mDisposables.isDisposed) {
            mDisposables.dispose()
        }
    }


}