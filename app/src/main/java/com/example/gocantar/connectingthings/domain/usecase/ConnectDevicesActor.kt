package com.example.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothDevice
import com.example.gocantar.connectingthings.common.enum.Event
import com.example.gocantar.connectingthings.common.ids.TypeID
import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.domain.interactor.ConnectDevicesInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by gocantar on 3/11/17.
 */
class ConnectDevicesActor @Inject constructor(private val mBLEService: BLEServiceBoundary)  : ConnectDevicesInteractor {

    private val mDisposables: CompositeDisposable = CompositeDisposable()

    override fun connect(device: BluetoothDevice, type: TypeID, disposable: DisposableObserver<Event>) {
        mBLEService.connect(device, type)
    }

    override fun disconnect(address: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dispose() {
        if (!mDisposables.isDisposed) {
            mDisposables.dispose()
        }
    }
}