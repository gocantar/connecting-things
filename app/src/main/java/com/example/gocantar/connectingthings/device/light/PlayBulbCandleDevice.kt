package com.example.gocantar.connectingthings.device.light

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattService
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
            val color = byteArrayOf(alpha.toByte(), red.toByte(), green.toByte(), blue.toByte())
            val service = getService(gatt)
            val characteristic = getColorCharacteristic(service)

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

    fun readCharacteristic(gatt: BluetoothGatt){
        gatt.readCharacteristic(getEffectCharacteristic(getService(gatt)))
    }

    private fun getEffectCharacteristic(service: BluetoothGattService) =
            service.getCharacteristic(CharacteristicUUIDs.PLAYBULB_CANDLE_CHANGE_EFFECT)

    private fun getColorCharacteristic(service: BluetoothGattService) =
            service.getCharacteristic(CharacteristicUUIDs.PLAYBULB_CANDLE_CHANGE_COLOR)

    private fun getService(gatt: BluetoothGatt) =
            gatt.getService(ServicesUUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE)

}