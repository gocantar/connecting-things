package com.example.gocantar.connectingthings.domain.entity

import android.bluetooth.BluetoothDevice
import android.os.ParcelUuid

/**
 * Created by gocantar on 13/10/17.
 */
data class BLEDevice(val bluetoothDevice: BluetoothDevice, val rssi: Int, val name: String, val uuids: List<ParcelUuid> ) {

    override fun hashCode(): Int {
        return bluetoothDevice.hashCode()
    }

}