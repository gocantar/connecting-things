package com.example.gocantar.connectingthings.domain.interactor

import android.bluetooth.BluetoothGatt

/**
 * Created by gocantar on 10/1/18.
 */
interface RequestLivePowerConsumptionInteractor {
    fun execute(gatt: BluetoothGatt)
    fun stop()
}