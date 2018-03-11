package com.gocantar.connectingthings.device.light

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattService
import android.util.Log
import com.gocantar.connectingthings.common.Constants
import com.gocantar.connectingthings.common.extension.toUnsignedInt
import com.gocantar.connectingthings.common.ids.CharacteristicUUIDs
import com.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.gocantar.connectingthings.domain.entity.BulbStatus
import io.reactivex.Observable
import java.util.*

/**
 * Created by gocantar on 11/3/18.
 */
object RbpiBulbDevice {

    private val TAG = javaClass.simpleName

    private const val ALPHA_VALUE = 0
    private const val RED_VALUE = 1
    private const val GREEN_VALUE = 2
    private const val BLUE_VALUE = 3



    val AVAILABLE_EFFECTS: List<Int> = listOf(Constants.COLOR_EFFECT)

    fun setColor(gatt: BluetoothGatt?, alpha: Int, red: Int, green: Int, blue: Int){
        gatt?.let {
            gatt?.let {
                val color = byteArrayOf(alpha.toByte(), red.toByte(), green.toByte(), blue.toByte())
                val service = getService(gatt)
                val characteristic = getCharacteristic(service)
                characteristic.value = color
                when(gatt.writeCharacteristic(characteristic)){
                    true -> Log.d(TAG, "Value was written")
                    else -> Log.d(TAG, "Error writing the value")
                }
            }
        }
    }

    fun readCharacteristic(gatt: BluetoothGatt){
        gatt.readCharacteristic(getCharacteristic(getService(gatt)))
    }

    fun decodeCharacteristic(value: ByteArray): Observable<BulbStatus> {
        return Observable.just(BulbStatus(true, getColorFromBytes(value),
                value[ALPHA_VALUE].toUnsignedInt() , Constants.COLOR_EFFECT))
    }

    private fun getColorFromBytes(color: ByteArray): Int {
        return  color[RED_VALUE].toUnsignedInt().shl(16) or
                color[GREEN_VALUE].toUnsignedInt().shl(8) or
                color[BLUE_VALUE].toUnsignedInt()
    }

    private fun getService(gatt: BluetoothGatt) =
            gatt.getService(ServicesUUIDs.RBPI3_PRIMARY_SERVICE)

    private fun getCharacteristic(service: BluetoothGattService) =
        service.getCharacteristic(CharacteristicUUIDs.RBPI3_CHANGE_COLOR)


}