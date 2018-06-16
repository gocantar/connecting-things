package com.gocantar.connectingthings.presentation.model

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.AppController
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.common.ids.TypeID

/**
 * Created by gocantar on 24/11/17.
 */

class BulbConnectedView(val device: BluetoothDevice, val name: String, val address: String,
                        val bluetoothGatt: BluetoothGatt?){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BulbConnectedView

        if (address != other.address) return false

        return true
    }

    override fun hashCode(): Int {
        return address.hashCode()
    }


}