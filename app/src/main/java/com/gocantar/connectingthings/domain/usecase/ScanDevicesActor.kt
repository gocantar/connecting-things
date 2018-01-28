package com.gocantar.connectingthings.domain.usecase

import com.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.gocantar.connectingthings.domain.entity.BLEDevice
import com.gocantar.connectingthings.domain.interactor.ScanDevicesInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by gocantar on 10/10/17.
 */

class ScanDevicesActor @Inject constructor(private val mBLEService: BLEServiceBoundary): ScanDevicesInteractor {

    private val mDisposables: CompositeDisposable = CompositeDisposable()

    override fun start(disposable: DisposableObserver<BLEDevice>){

        doAsync { mBLEService.start() }

        val observable = mBLEService.mPublisherOfBLEDevice
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        mDisposables.add(observable.subscribeWith(disposable))

    }

    override fun stop(){
        doAsync {
            mBLEService.stop()
            mDisposables.clear()
        }
    }

    override fun dispose(){
        if (!mDisposables.isDisposed) {
            mDisposables.dispose()
        }
    }


}