package com.example.gocantar.connectingthings.domain.interactor

import android.bluetooth.BluetoothGatt

/**
 * Created by gocantar on 10/1/18.
 */
interface SetPlugStatusInteractor {
    fun turnOn(gatt: BluetoothGatt)
    fun turnOff(gatt: BluetoothGatt)
}