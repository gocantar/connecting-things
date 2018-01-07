package com.example.gocantar.connectingthings.device.controller

import android.bluetooth.BluetoothGatt
import android.graphics.Color
import android.os.ParcelUuid
import com.example.gocantar.connectingthings.common.extension.getBulbService
import com.example.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.example.gocantar.connectingthings.device.light.PlayBulbCandleDevice
import com.example.gocantar.connectingthings.domain.boundary.BulbControllerBoundary
import com.example.gocantar.connectingthings.domain.entity.BulbParams

/**
 * Created by gocantar on 3/11/17.
 */
class BulbController: BulbControllerBoundary {

    private val TAG = javaClass.simpleName

    override fun setColor(params: BulbParams) {
        when(params.device.uuids.getBulbService()){
            ParcelUuid(ServicesUUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE) -> PlayBulbCandleDevice()
                    .setColor(gatt = params.device.gattBluetoothGatt,
                            alpha = params.alpha, red = Color.red(params.color),
                            green = Color.green(params.color), blue = Color.blue(params.color))
        }
    }

    override fun readCharacteristic(gatt: BluetoothGatt) {
        when(gatt.services.map { ParcelUuid(it.uuid) }.getBulbService()){
            ParcelUuid(ServicesUUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE) -> PlayBulbCandleDevice()
                    .readCharacteristic(gatt)
        }
    }
}