package com.example.gocantar.connectingthings.domain.boundary

import android.bluetooth.BluetoothGatt
import com.example.gocantar.connectingthings.domain.entity.CharacteristicData

/**
 * Created by gocantar on 10/1/18.
 */
interface PlugControllerBoundary {
    fun turnOn(gatt: BluetoothGatt)
    fun turnOff(gatt: BluetoothGatt)
    fun requestPowerConsumption(gatt: BluetoothGatt)
    fun decodePowerConsumption(gatt: BluetoothGatt, charData: CharacteristicData): Int
    fun enableNotifications(gatt: BluetoothGatt)
    fun disableNotifications(gatt: BluetoothGatt)
}