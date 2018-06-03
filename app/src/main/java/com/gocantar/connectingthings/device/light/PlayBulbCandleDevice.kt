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

class PlayBulbCandleDevice :BulbDevice{


    private val TAG = javaClass.simpleName

    private  val CANDLE_EFFECT = 4
    private  val FADE_EFFECT =  1
    private  val PULSE_EFFECT = 0
    private  val DECREASE_EFFECT =  3
    private  val RAINBOW_EFFECT = 2

    private  val ALPHA_VALUE = 0
    private  val RED_VALUE = 1
    private  val GREEN_VALUE = 2
    private  val BLUE_VALUE = 3
    private  val EFFECT_VALUE = 4
    private  val PERIOD_VALUE = 5

    private val AVAILABLE_EFFECTS: List<Int> = listOf(Constants.COLOR_EFFECT, Constants.CANDLE_EFFECT,
                Constants.FADE_EFFECT, Constants.PULSE_EFFECT, Constants.DECREASE_EFFECT,
                Constants.RAINBOW_EFFECT)


    override fun setColor(gatt: BluetoothGatt?, alpha: Int, red: Int, green: Int, blue: Int){
        gatt?.let {
            val color = byteArrayOf(alpha.toByte(), red.toByte(), green.toByte(), blue.toByte())
            val service = getService(gatt)
            val characteristic = getColorCharacteristic(service)
            characteristic.value = color
            when(gatt.writeCharacteristic(characteristic)){
                true -> Log.d(TAG, "Value was written")
                else -> Log.d(TAG, "Error writing the value")
            }
        }
    }

    override fun setEffect(gatt: BluetoothGatt?, alpha: Int, period: Int, red: Int , green: Int , blue: Int, effect: Int){
        gatt?.let {
            val effect = byteArrayOf(alpha.toByte(), red.toByte(), green.toByte(), blue.toByte(), getEffectByteFromID(effect), 0x00, period.toByte() , 0x00)
            val service = getService(gatt)
            val characteristic = getEffectCharacteristic(service)
            characteristic.value = effect
            when (gatt.writeCharacteristic(characteristic)){
                true -> Log.d(TAG, "Value was written")
                else -> Log.d(TAG, "Error writing the value")
            }
        }
    }

    override fun readCharacteristic(gatt: BluetoothGatt){
        gatt.readCharacteristic(getEffectCharacteristic(getService(gatt)))
    }

    override fun decodeCharacteristic(charData: CharacteristicData): Observable<BulbStatus> {
        var params: BulbStatus? = null
        when(charData.uuid){
            CharacteristicUUIDs.PLAYBULB_CANDLE_CHANGE_EFFECT -> {
                params = decodeEffectCharacteristic(charData.value)
            }
        }
        return Observable.just(params)
    }

    override fun getAvailableEffects(): Observable<Int> {
        return Observable.just(AVAILABLE_EFFECTS).flatMapIterable{ it }
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
            CANDLE_EFFECT -> Constants.CANDLE_EFFECT
            FADE_EFFECT -> Constants.FADE_EFFECT
            PULSE_EFFECT -> Constants.PULSE_EFFECT
            DECREASE_EFFECT -> Constants.DECREASE_EFFECT
            RAINBOW_EFFECT -> Constants.RAINBOW_EFFECT
            else -> Constants.COLOR_EFFECT
    }

    private fun getEffectByteFromID(effect: Int): Byte =
            when(effect){
                Constants.CANDLE_EFFECT -> CANDLE_EFFECT.toByte()
                Constants.FADE_EFFECT -> FADE_EFFECT.toByte()
                Constants.DECREASE_EFFECT -> DECREASE_EFFECT.toByte()
                Constants.PULSE_EFFECT -> PULSE_EFFECT.toByte()
                Constants.RAINBOW_EFFECT -> RAINBOW_EFFECT.toByte()
                else -> 0x00
            }

    private fun getColorFromBytes(color: ByteArray): Int {
        return  color[ALPHA_VALUE].toUnsignedInt().shl(24) or
                color[RED_VALUE].toUnsignedInt().shl(16) or
                color[GREEN_VALUE].toUnsignedInt().shl(8) or
                color[BLUE_VALUE].toUnsignedInt()
    }

}