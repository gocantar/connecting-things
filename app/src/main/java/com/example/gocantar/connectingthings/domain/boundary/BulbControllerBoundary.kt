package com.example.gocantar.connectingthings.domain.boundary

import android.bluetooth.BluetoothGatt
import com.example.gocantar.connectingthings.domain.entity.BulbParams

/**
 * Created by gocantar on 6/11/17.
 */
interface BulbControllerBoundary {
    fun setColor(params: BulbParams)
    fun readCharacteristic(gatt: BluetoothGatt)
}