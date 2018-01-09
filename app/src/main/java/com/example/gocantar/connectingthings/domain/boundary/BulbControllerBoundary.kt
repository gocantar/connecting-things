package com.example.gocantar.connectingthings.domain.boundary

import android.bluetooth.BluetoothGatt
import com.example.gocantar.connectingthings.domain.entity.BulbParams
import com.example.gocantar.connectingthings.domain.entity.BulbStatus
import com.example.gocantar.connectingthings.domain.entity.CharacteristicData
import io.reactivex.Observable

/**
 * Created by gocantar on 6/11/17.
 */
interface BulbControllerBoundary {
    fun setColor(params: BulbParams)
    fun readCharacteristic(gatt: BluetoothGatt)
    fun decodeStatus(gatt: BluetoothGatt, charData: CharacteristicData): Observable<BulbStatus>
    fun getAvailableEffects(): Observable<Int>
}