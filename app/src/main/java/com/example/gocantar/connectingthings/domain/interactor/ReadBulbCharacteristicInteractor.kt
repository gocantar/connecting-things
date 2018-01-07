package com.example.gocantar.connectingthings.domain.interactor

import android.bluetooth.BluetoothGatt

/**
 * Created by gocantar on 5/1/18.
 */
interface ReadBulbCharacteristicInteractor {
    fun execute(gatt: BluetoothGatt)
}