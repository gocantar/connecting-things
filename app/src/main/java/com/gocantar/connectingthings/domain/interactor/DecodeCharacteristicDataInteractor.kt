package com.gocantar.connectingthings.domain.interactor

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.domain.entity.CharacteristicData

/**
 * Created by gocantar on 11/1/18.
 */
interface DecodeCharacteristicDataInteractor<T> {
    fun decode(gatt: BluetoothGatt? = null, charData: CharacteristicData): T
}