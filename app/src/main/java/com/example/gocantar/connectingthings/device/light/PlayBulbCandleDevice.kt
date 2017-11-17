package com.example.gocantar.connectingthings.device.light

import android.bluetooth.BluetoothGatt

import com.example.gocantar.connectingthings.common.ids.UUIDs


/**
 * Created by gocantar on 7/11/17.
 */

class PlayBulbCandleDevice{

    private val TAG = javaClass.simpleName

    fun setColor(gatt: BluetoothGatt?, alpha: String, red: String, blue: String, green:String){
        gatt?.let {

            val service = gatt.getService(UUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE)
            val characteristic = service.getCharacteristic(UUIDs.PLAYBULB_CANDLE_COLOR_CHARACTERISTIC)


        }
    }


}