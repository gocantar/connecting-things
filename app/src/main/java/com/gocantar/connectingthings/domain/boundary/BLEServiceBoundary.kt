package com.gocantar.connectingthings.domain.boundary

import android.bluetooth.BluetoothDevice
import android.content.Intent
import com.gocantar.connectingthings.common.ids.TypeID
import com.gocantar.connectingthings.domain.entity.BLEDevice
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.entity.DeviceEvent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by gocantar on 13/10/17.
 */
interface BLEServiceBoundary {

    val mPublisherOfBLEDevice: PublishSubject<BLEDevice>
    val mPublisherOfEvent: PublishSubject<DeviceEvent>
    val mPublisherOfCharacteristic: PublishSubject<CharacteristicData>

    fun start()
    fun stop()
    fun connect(bluetoothDevice: BluetoothDevice, typeID: TypeID)
    fun disconnect(address: String)
    fun enableBLE()
    fun getConnectedDevices(): Observable<BLEDevice>
    fun getDevice(address: String): Observable<BLEDevice>
    fun getRequestBLEIntent(): Intent

}