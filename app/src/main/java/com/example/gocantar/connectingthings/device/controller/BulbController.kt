package com.example.gocantar.connectingthings.device.controller

import android.bluetooth.BluetoothGatt
import android.graphics.Color
import android.os.ParcelUuid
import com.example.gocantar.connectingthings.common.extension.getBulbService
import com.example.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.example.gocantar.connectingthings.device.light.PlayBulbCandleDevice
import com.example.gocantar.connectingthings.domain.boundary.BulbControllerBoundary
import com.example.gocantar.connectingthings.domain.entity.BulbParams
import com.example.gocantar.connectingthings.domain.entity.BulbStatus
import com.example.gocantar.connectingthings.domain.entity.CharacteristicData
import io.reactivex.Observable

/**
 * Created by gocantar on 3/11/17.
 */
class BulbController: BulbControllerBoundary {

    private val TAG = javaClass.simpleName

    override fun setColor(params: BulbParams) {
        when(params.device.uuids.getBulbService()){
            ParcelUuid(ServicesUUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE) -> PlayBulbCandleDevice()
                    .setColor(gatt = params.device.gattBluetoothGatt,
                            alpha = params.status.alpha, red = Color.red(params.status.color),
                            green = Color.green(params.status.color), blue = Color.blue(params.status.color))
        }
    }

    override fun readCharacteristic(gatt: BluetoothGatt) {
        when(gatt.services.map { ParcelUuid(it.uuid) }.getBulbService()){
            ParcelUuid(ServicesUUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE) -> PlayBulbCandleDevice()
                    .readCharacteristic(gatt)
        }
    }

    override fun decodeStatus(gatt: BluetoothGatt, charData: CharacteristicData): Observable<BulbStatus> {
        return when(gatt.services.map { ParcelUuid(it.uuid) }.getBulbService()){
            ParcelUuid(ServicesUUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE) -> {
                PlayBulbCandleDevice().decodeCharacteristic(charData)
            }
            else -> Observable.empty()
        }
    }

    override fun getAvailableEffects(): Observable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}