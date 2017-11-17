package com.example.gocantar.connectingthings.device.controller

import android.graphics.Color
import android.os.ParcelUuid
import com.example.gocantar.connectingthings.common.extension.toHex
import com.example.gocantar.connectingthings.common.ids.UUIDs
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
            uuids.find { it == ParcelUuid(UUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE) } != null
                -> PlayBulbCandleDevice().setColor(gatt = params.device.gattBluetoothGatt, alpha = params.alpha.toHex(), red = Color.red(params.color).toHex(),
                    green = Color.green(params.color).toHex(), blue = Color.blue(params.color).toHex())

        }
    }




}