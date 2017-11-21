package com.example.gocantar.connectingthings.device.light

import android.bluetooth.BluetoothGatt
import android.util.Log
import com.example.gocantar.connectingthings.common.ids.CharacteristicUUIDs

import com.example.gocantar.connectingthings.common.ids.ServicesUUIDs



/**
 * Created by gocantar on 7/11/17.
 */

class PlayBulbCandleDevice{

    private val TAG = javaClass.simpleName

    fun setColor(gatt: BluetoothGatt?, alpha: Int, red: Int, green: Int, blue: Int){
        gatt?.let {

            val color = byteArrayOf(alpha.toByte(),  red.toByte() , green.toByte(),  blue.toByte())

            val service = gatt.getService(ServicesUUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE)
            val characteristic = service.getCharacteristic(CharacteristicUUIDs.PLAYBULB_CANDLE_CHANGE_COLOR)


            characteristic.value = color
            val status = gatt.writeCharacteristic(characteristic)

            when{
                status -> Log.d(TAG, "Value was written")
                else -> {
                    Log.d(TAG, "Error writing the value")
                }
            }

        }
    }



}