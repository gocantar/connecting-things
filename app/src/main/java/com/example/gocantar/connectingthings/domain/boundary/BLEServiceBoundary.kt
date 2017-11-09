package com.example.gocantar.connectingthings.domain.boundary

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.util.EventLog
import com.example.gocantar.connectingthings.common.enum.Event
import com.example.gocantar.connectingthings.common.ids.TypeID
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import io.reactivex.subjects.PublishSubject

/**
 * Created by gocantar on 13/10/17.
 */
interface BLEServiceBoundary {

    val mPublisherOfBLEDevice: PublishSubject<BLEDevice>
    val mPublisherOfEvent: PublishSubject<Event>

    fun start()
    fun stop()
    fun connect(bluetoothDevice: BluetoothDevice, typeID: TypeID)
    fun disconnect(bluetoothDevice: BluetoothDevice)
    fun isBLEnabled(): Boolean
    fun getConnectedDevices(): List<BLEDevice>
    fun getRequestBLEIntent(): Intent
}