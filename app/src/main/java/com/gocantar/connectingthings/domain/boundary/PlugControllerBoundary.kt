package com.gocantar.connectingthings.domain.boundary

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.domain.entity.CharacteristicData

/**
 * Created by gocantar on 10/1/18.
 */
interface PlugControllerBoundary: ManageNotificationsBoundary {
    fun turnOn(gatt: BluetoothGatt)
    fun turnOff(gatt: BluetoothGatt)
    fun requestPowerConsumption(gatt: BluetoothGatt)
    fun decodePowerConsumption(gatt: BluetoothGatt, charData: CharacteristicData): Int
}