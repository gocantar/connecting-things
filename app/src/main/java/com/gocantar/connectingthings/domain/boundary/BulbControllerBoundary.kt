package com.gocantar.connectingthings.domain.boundary

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.domain.entity.BulbParams
import com.gocantar.connectingthings.domain.entity.BulbStatus
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import io.reactivex.Observable

/**
 * Created by gocantar on 6/11/17.
 */
interface BulbControllerBoundary {
    fun setColor(params: BulbParams)
    fun setEffect(params: BulbParams)
    fun requestStatus(gatt: BluetoothGatt)
    fun decodeStatus(gatt: BluetoothGatt, charData: CharacteristicData): Observable<BulbStatus>
    fun getAvailableEffects(gatt: BluetoothGatt): Observable<Int>
}