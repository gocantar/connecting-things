package com.gocantar.connectingthings.device.controller

import android.bluetooth.BluetoothGatt
import android.graphics.Color
import android.os.ParcelUuid
import com.gocantar.connectingthings.common.extension.getBulbServiceUuid
import com.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.gocantar.connectingthings.device.light.PlayBulbCandleDevice
import com.gocantar.connectingthings.domain.boundary.BulbControllerBoundary
import com.gocantar.connectingthings.domain.entity.BulbParams
import com.gocantar.connectingthings.domain.entity.BulbStatus
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import io.reactivex.Observable

/**
 * Created by gocantar on 3/11/17.
 */
class BulbController: BulbControllerBoundary {

    private val TAG = javaClass.simpleName

    override fun setColor(params: BulbParams) {
        when(params.device.uuids.getBulbServiceUuid()){
            ParcelUuid(ServicesUUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE) -> PlayBulbCandleDevice
                    .setColor(gatt = params.device.gattBluetoothGatt,
                            alpha = params.status.alpha, red = Color.red(params.status.color),
                            green = Color.green(params.status.color), blue = Color.blue(params.status.color))
        }
    }

    override fun requestStatus(gatt: BluetoothGatt) {
        when(gatt.services.map { ParcelUuid(it.uuid) }.getBulbServiceUuid()){
            ParcelUuid(ServicesUUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE) -> PlayBulbCandleDevice
                    .readCharacteristic(gatt)
        }
    }

    override fun decodeStatus(gatt: BluetoothGatt, charData: CharacteristicData): Observable<BulbStatus> {
        return when(gatt.services.map { ParcelUuid(it.uuid) }.getBulbServiceUuid()){
            ParcelUuid(ServicesUUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE) -> {
                PlayBulbCandleDevice.decodeCharacteristic(charData)
            }
            else -> Observable.empty()
        }
    }

    override fun getAvailableEffects(): Observable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}