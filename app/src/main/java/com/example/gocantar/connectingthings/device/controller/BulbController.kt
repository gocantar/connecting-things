package com.example.gocantar.connectingthings.device.controller

import android.graphics.Color
import android.os.ParcelUuid
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
        val uuids = params.device.uuids
        when{
            uuids.find { it == ParcelUuid(ServicesUUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE) } != null
                -> PlayBulbCandleDevice().setColor(gatt = params.device.gattBluetoothGatt, alpha = params.alpha, red = Color.red(params.color),
                    green = Color.green(params.color), blue = Color.blue(params.color))

        }
    }




}