package com.gocantar.connectingthings.device.light

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattService
import android.util.Log
import com.gocantar.connectingthings.common.Constants
import com.gocantar.connectingthings.common.extension.toUnsignedInt
import com.gocantar.connectingthings.common.ids.CharacteristicUUIDs
import com.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.gocantar.connectingthings.domain.entity.BulbStatus
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import io.reactivex.Observable


/**
 * Created by gocantar on 7/11/17.
 */

object PlayBulbCandleDevice{

    private val TAG = javaClass.simpleName

    private val COLOR_EFFECT =  1
    private val CANDLE_EFFECT = 2
    private val FADE_EFFECT =  3
    private val PULSE_EFFECT = 4
    private val DECREASE_EFFECT =  5
    private val RAINBOW_EFFECT = 6

    private val ALPHA_VALUE = 0
    private val RED_VALUE = 1
    private val GREEN_VALUE = 2
    private val BLUE_VALUE = 3
    private val EFFECT_VALUE = 4
    private val PERIOD_VALUE = 6

    val AVAILABLE_EFFECTS: List<Int> = listOf(Constants.COLOR_EFFECT, Constants.CANDLE_EFFECT,
                Constants.FADE_EFFECT, Constants.PULSE_EFFECT, Constants.DECREASE_EFFECT,
                Constants.RAINBOW_EFFECT)


    fun setColor(gatt: BluetoothGatt?, alpha: Int, red: Int, green: Int, blue: Int){
        gatt?.let {
            val color = byteArrayOf(alpha.toByte(), red.toByte(), green.toByte(), blue.toByte())
            val service = getService(gatt)
            val characteristic = getColorCharacteristic(service)
            characteristic.value = color
            when(gatt.writeCharacteristic(characteristic)){
                true -> Log.d(TAG, "Value was written")
                else -> { Log.d(TAG, "Error writing the value") }
            }
        }
    }

    fun readCharacteristic(gatt: BluetoothGatt){
        gatt.readCharacteristic(getEffectCharacteristic(getService(gatt)))
    }

    fun decodeCharacteristic(charData: CharacteristicData): Observable<BulbStatus> {
        var params: BulbStatus? = null
        when(charData.uuid){
            CharacteristicUUIDs.PLAYBULB_CANDLE_CHANGE_EFFECT -> {
                params = decodeEffectCharacteristic(charData.value)
            }
        }
        return Observable.just(params)
    }

    private fun decodeEffectCharacteristic(value: ByteArray): BulbStatus?{
        val effect = value[EFFECT_VALUE].toUnsignedInt()
        return BulbStatus(true, getColorFromBytes(value),
                value[ALPHA_VALUE].toUnsignedInt() , getEffectID(effect) )
    }

    private fun getEffectCharacteristic(service: BluetoothGattService) =
            service.getCharacteristic(CharacteristicUUIDs.PLAYBULB_CANDLE_CHANGE_EFFECT)

    private fun getColorCharacteristic(service: BluetoothGattService) =
            service.getCharacteristic(CharacteristicUUIDs.PLAYBULB_CANDLE_CHANGE_COLOR)

    private fun getService(gatt: BluetoothGatt) =
            gatt.getService(ServicesUUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE)

    private fun getEffectID(effect: Int): Int =
        when(effect){
            COLOR_EFFECT -> Constants.COLOR_EFFECT
            CANDLE_EFFECT -> Constants.CANDLE_EFFECT
            FADE_EFFECT -> Constants.FADE_EFFECT
            PULSE_EFFECT -> Constants.PULSE_EFFECT
            DECREASE_EFFECT -> Constants.DECREASE_EFFECT
            RAINBOW_EFFECT -> Constants.RAINBOW_EFFECT
            else -> Constants.COLOR_EFFECT
    }

    private fun getColorFromBytes(color: ByteArray): Int {
        return  color[RED_VALUE].toUnsignedInt().shl(16) or
                color[GREEN_VALUE].toUnsignedInt().shl(8) or
                color[BLUE_VALUE].toUnsignedInt()
    }

}