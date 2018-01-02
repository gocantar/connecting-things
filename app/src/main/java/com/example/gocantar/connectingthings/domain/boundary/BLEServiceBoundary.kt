package com.example.gocantar.connectingthings.domain.boundary

import android.bluetooth.BluetoothDevice
import android.content.Intent
import com.example.gocantar.connectingthings.common.ids.TypeID
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.domain.entity.DeviceEvent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by gocantar on 13/10/17.
 */
interface BLEServiceBoundary {

    val mPublisherOfBLEDevice: PublishSubject<BLEDevice>
    val mPublisherOfEvent: PublishSubject<DeviceEvent>

    fun start()
    fun stop()
    fun connect(bluetoothDevice: BluetoothDevice, typeID: TypeID)
    fun disconnect(address: String)
    fun isBLEnabled(): Boolean
    fun getConnectedDevices(): Observable<BLEDevice>
    fun getDevice(address: String): Observable<BLEDevice>
    fun getRequestBLEIntent(): Intent

}