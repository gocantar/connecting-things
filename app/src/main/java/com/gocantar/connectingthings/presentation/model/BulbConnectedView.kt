package com.example.gocantar.connectingthings.presentation.model

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import com.example.gocantar.connectingthings.AppController
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.common.ids.TypeID

/**
 * Created by gocantar on 24/11/17.
 */

class BulbConnectedView(val device: BluetoothDevice, val name: String, val address: String,
                        val bluetoothGatt: BluetoothGatt?){
    init {
        var effect: Int = 0
        var color: Int = AppController.instance.getColor( R.color.white )
        var typeId = TypeID.BULB
    }

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