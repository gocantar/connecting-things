package com.example.gocantar.connectingthings.device.plug

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.util.Log
import com.example.gocantar.connectingthings.common.extension.hexStringToByteArray
import com.example.gocantar.connectingthings.common.extension.toInt
import com.example.gocantar.connectingthings.common.ids.CharacteristicUUIDs
import com.example.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.example.gocantar.connectingthings.domain.entity.CharacteristicData


/**
 * Created by gocantar on 10/1/18.
 */
object RevogiPlug {

    private val TAG = javaClass.simpleName

    private val TURN_ON_REQUEST: ByteArray = "0F06030001000005FFFF".hexStringToByteArray()
    private val TURN_OFF_REQUEST: ByteArray = "0F06030000000004FFFF".hexStringToByteArray()
    private val POWER_CONSUMPTION_REQUEST: ByteArray = "0F050400000005FFFF".hexStringToByteArray()

    fun turnOn(gatt: BluetoothGatt){
       val characteristic = getRequestCharacteristic(gatt, TURN_ON_REQUEST)
        when(gatt.writeCharacteristic(characteristic)){
            true -> Log.d(TAG, "Revogi Plug has been turned on")
            else -> { Log.d(TAG, "Error writing the value") }
        }
    }

    fun turnOff(gatt: BluetoothGatt){
        val characteristic = getRequestCharacteristic(gatt, TURN_OFF_REQUEST)
        when(gatt.writeCharacteristic(characteristic)){
            true -> Log.d(TAG, "Revogi Plug has been turned off")
            else -> { Log.d(TAG, "Error writing the value") }
        }
    }

    fun requestPoweConsumption(gatt: BluetoothGatt){
        val characteristic = getRequestCharacteristic(gatt, POWER_CONSUMPTION_REQUEST)
        when(gatt.writeCharacteristic(characteristic)){
            true -> Log.d(TAG, "The live power consumptions has been requested")
            else -> { Log.d(TAG, "Error writing the value") }
        }
    }

    fun enableNotifications(gatt: BluetoothGatt){
        val characteristic = getResponseCharacteristic(gatt)
        gatt.setCharacteristicNotification(characteristic, true)
        writeConfigCharacteristicDescriptor(gatt, characteristic, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
    }

    fun disableNotifications(gatt: BluetoothGatt){
        val characteristic = getResponseCharacteristic(gatt)
        gatt.setCharacteristicNotification(characteristic, false)
        writeConfigCharacteristicDescriptor(gatt, characteristic, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)
    }

    fun decodePowerConsumption(charData: CharacteristicData): Int{
        return when(charData.uuid){
            CharacteristicUUIDs.REVOGI_SMART_PLUG_RESPONSE ->{
                TODO("Return state and power consumption")
                val state = charData.value[4]
                charData.value.copyOfRange(5,10).toInt()
            }
            else -> 0
        }
    }

    private fun getRequestCharacteristic(gatt: BluetoothGatt, value: ByteArray? = null): BluetoothGattCharacteristic {
        val characteristic = gatt.getService(ServicesUUIDs.REVOGI_SMART_PLUG_PRIMARY_SERVICE)
                .getCharacteristic(CharacteristicUUIDs.REVOGI_SMART_PLUG_REQUEST)
        when{
            value != null -> characteristic.value = value
        }
        return characteristic
    }

    private fun getResponseCharacteristic(gatt: BluetoothGatt): BluetoothGattCharacteristic{
        return gatt.getService(ServicesUUIDs.REVOGI_SMART_PLUG_PRIMARY_SERVICE)
                .getCharacteristic(CharacteristicUUIDs.REVOGI_SMART_PLUG_RESPONSE)
    }



    private fun writeConfigCharacteristicDescriptor(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic,
                                                    value: ByteArray?){

        val descriptor = characteristic.getDescriptor(CharacteristicUUIDs.DESCTRIPTOR_CONFIG_CHARACTERISTIC)
        descriptor.value = value
        gatt.writeDescriptor(descriptor)
    }

}