package com.example.gocantar.connectingthings.presentation.model

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import com.example.gocantar.connectingthings.common.ids.TypeID

/**
 * Created by gocantar on 13/10/17.
 */
data class BLEDeviceView(val device: BluetoothDevice, val name: String = "N/A", val mac_address: String,
                         val numberOfServices: Int, val typeID: TypeID,
                         val bluetoothGatt: BluetoothGatt? ,val rssi: Int = 0 ) {

    override fun equals(other: Any?): Boolean {
        val o = other as BLEDeviceView
        return o.mac_address == mac_address
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + mac_address.hashCode()
        return result
    }


}