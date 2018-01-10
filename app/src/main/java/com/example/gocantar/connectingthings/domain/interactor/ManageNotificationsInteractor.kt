package com.example.gocantar.connectingthings.domain.interactor

import android.bluetooth.BluetoothGatt


/**
 * Created by gocantar on 10/1/18.
 */
interface ManageNotificationsInteractor{
    fun enable(gatt: BluetoothGatt)
    fun disable(gatt: BluetoothGatt)
}