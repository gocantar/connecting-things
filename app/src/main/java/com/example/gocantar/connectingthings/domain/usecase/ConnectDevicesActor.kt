package com.example.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothDevice
import com.example.gocantar.connectingthings.common.enum.Event
import com.example.gocantar.connectingthings.common.ids.TypeID
import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.domain.entity.DeviceEvent
import com.example.gocantar.connectingthings.domain.interactor.ConnectDevicesInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by gocantar on 3/11/17.
 */
class ConnectDevicesActor @Inject constructor(private val mBLEService: BLEServiceBoundary)  : ConnectDevicesInteractor {

    private val mDisposables: CompositeDisposable = CompositeDisposable()

    override fun connect(device: BluetoothDevice, type: TypeID, disposable: DisposableObserver<DeviceEvent>, subscribe: Boolean) {
        mBLEService.connect(device, type)

        if (subscribe) {
            val observable = mBLEService.mPublisherOfEvent
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

            mDisposables.addAll(observable.subscribeWith(disposable))
        }
    }

    override fun disconnect(address: String) {
        mBLEService.disconnect(address)
    }

    override fun dispose() {
        if (!mDisposables.isDisposed) {
            mDisposables.dispose()
        }
    }
}