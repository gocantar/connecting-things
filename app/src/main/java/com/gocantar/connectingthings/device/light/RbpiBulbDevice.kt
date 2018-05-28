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
import java.util.*

/**
 * Created by gocantar on 11/3/18.
 */
class RbpiBulbDevice :BulbDevice {

    private val TAG = javaClass.simpleName

    private  val ALPHA_VALUE = 0
    private  val RED_VALUE = 1
    private  val GREEN_VALUE = 2
    private  val BLUE_VALUE = 3



    val AVAILABLE_EFFECTS: List<Int> = listOf(Constants.COLOR_EFFECT)

    override fun setColor(gatt: BluetoothGatt?, alpha: Int, red: Int, green: Int, blue: Int){
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

    override fun readCharacteristic(gatt: BluetoothGatt){
        gatt.readCharacteristic(getCharacteristic(getService(gatt)))
    }

    override fun decodeCharacteristic(charData: CharacteristicData): Observable<BulbStatus> {
        return Observable.just(BulbStatus(true, getColorFromBytes(charData.value),
                charData.value[ALPHA_VALUE].toUnsignedInt() , Constants.COLOR_EFFECT))
    }

    override fun setEffect(gatt: BluetoothGatt?, alpha: Int, period: Int, red: Int, green: Int, blue: Int, effect: Int) {
        Log.e(TAG, "This method can not be called")
    }

    override fun getAvailableEffects(): Observable<Int> {
        return Observable.just(AVAILABLE_EFFECTS).flatMapIterable{ it }
    }

    private fun getColorFromBytes(color: ByteArray): Int {
        return  color[ALPHA_VALUE].toUnsignedInt().shl(24) or
                color[RED_VALUE].toUnsignedInt().shl(16) or
                color[GREEN_VALUE].toUnsignedInt().shl(8) or
                color[BLUE_VALUE].toUnsignedInt()
    }

    private fun getService(gatt: BluetoothGatt) =
            gatt.getService(ServicesUUIDs.RBPI3_PRIMARY_SERVICE)

    private fun getCharacteristic(service: BluetoothGattService) =
        service.getCharacteristic(CharacteristicUUIDs.RBPI3_CHANGE_COLOR)


}