package com.gocantar.connectingthings.device.light

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.domain.entity.BulbStatus
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import io.reactivex.Observable

/**
 * Created by gocantar on 11/3/18.
 */
interface BulbDevice {
    fun setColor(gatt: BluetoothGatt?, alpha: Int, red: Int, green: Int, blue: Int)
    fun setEffect(gatt: BluetoothGatt?, alpha: Int, period: Int, red: Int = 0x00, green: Int = 0x00, blue: Int = 0x00, effect: Int)
    fun readCharacteristic(gatt: BluetoothGatt)
    fun decodeCharacteristic(charData: CharacteristicData): Observable<BulbStatus>
}