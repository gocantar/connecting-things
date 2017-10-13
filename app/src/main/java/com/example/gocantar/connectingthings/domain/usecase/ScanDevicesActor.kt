package com.example.gocantar.connectingthings.domain.usecase

import com.example.gocantar.connectingthings.data.BLEService
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by gocantar on 10/10/17.
 */
class ScanDevicesActor {

    private val mDisposables: CompositeDisposable = CompositeDisposable()

    fun execute(discovered: (BLEDevice) -> Unit){

        BLEService.start()

        val observable = BLEService.mPublisher
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())


        mDisposables.add( observable.subscribeWith(object : DisposableObserver<BLEDevice>(){
            override fun onNext(t: BLEDevice) {
                discovered(t)
            }
            override fun onError(e: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onComplete() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }))

    }

    fun stop(){
        BLEService.stop()
    }

    fun dispose(){
        if (!mDisposables.isDisposed) {
            mDisposables.dispose()
        }
    }


}