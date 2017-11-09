package com.example.gocantar.connectingthings.domain.interactor

import android.bluetooth.BluetoothDevice
import com.example.gocantar.connectingthings.common.enum.Event
import com.example.gocantar.connectingthings.common.ids.TypeID
import io.reactivex.observers.DisposableObserver

/**
 * Created by gocantar on 3/11/17.
 */
interface ConnectDevicesInteractor {
    fun connect(device: BluetoothDevice, type: TypeID ,disposable: DisposableObserver<Event>)
    fun disconnect(address: String)
    fun dispose()
}