package com.example.gocantar.connectingthings.domain.interactor

import android.bluetooth.BluetoothGatt
import com.example.gocantar.connectingthings.domain.entity.CharacteristicData

/**
 * Created by gocantar on 11/1/18.
 */
interface DecodeLivePowerConsumptionInteractor {
    fun decode(gatt: BluetoothGatt, charData: CharacteristicData): Int
}