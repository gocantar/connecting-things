package com.gocantar.connectingthings.device.sensor

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.os.ParcelUuid
import com.gocantar.connectingthings.common.enum.SensorType
import com.gocantar.connectingthings.common.ids.CharacteristicUUIDs
import com.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.entity.SensorData
import org.w3c.dom.CharacterData

/**
 * Created by gocantar on 23/1/18.
 */
object WeatherStationArduino101 {


    fun enableTemperatureNotification(gatt: BluetoothGatt){
        val characteristic = getTemperatureCharacteristic(gatt)
        gatt.setCharacteristicNotification(characteristic, true)
        writeConfigCharacteristicDescriptor(gatt, characteristic, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
    }

    fun disableTemperatureNotification(gatt: BluetoothGatt){
        val characteristic = getTemperatureCharacteristic(gatt)
        gatt.setCharacteristicNotification(characteristic, false)
        writeConfigCharacteristicDescriptor(gatt, characteristic, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)
    }

    fun enableHumidityNotification(gatt: BluetoothGatt){
        val characteristic = getHumidityCharacteristic(gatt)
        gatt.setCharacteristicNotification(characteristic, true)
        writeConfigCharacteristicDescriptor(gatt, characteristic, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
    }

    fun disableHumidityNotification(gatt: BluetoothGatt){
        val characteristic = getHumidityCharacteristic(gatt)
        gatt.setCharacteristicNotification(characteristic, false)
        writeConfigCharacteristicDescriptor(gatt, characteristic, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)
    }

    fun decodeData(data: CharacteristicData): SensorData{
        return when(data.uuid){
            CharacteristicUUIDs.ARDUINO101_TEMPERATURE -> {
                SensorData(data.value.first().toInt(), data.address, SensorType.TEMPERATURE)
            }
            else -> SensorData(data.value.first().toInt(), data.address, SensorType.HUMIDITY)
        }
    }

    /**
     * Private functions
     */

    private fun getTemperatureCharacteristic(gatt: BluetoothGatt): BluetoothGattCharacteristic{
        return gatt.getService(ServicesUUIDs.ARDUINO101_WEATHER_STATION_SERVICE)
                .getCharacteristic(CharacteristicUUIDs.ARDUINO101_TEMPERATURE)
    }

    private fun getHumidityCharacteristic(gatt: BluetoothGatt): BluetoothGattCharacteristic{
        return  gatt.getService(ServicesUUIDs.ARDUINO101_WEATHER_STATION_SERVICE)
                .getCharacteristic(CharacteristicUUIDs.ARDUINO101_HUMIDITY)
    }

    private fun writeConfigCharacteristicDescriptor(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic,
                                                    value: ByteArray?){
        val descriptor = characteristic.getDescriptor(CharacteristicUUIDs.DESCTRIPTOR_CONFIG_CHARACTERISTIC)
        descriptor.value = value
        gatt.writeDescriptor(descriptor)
    }
}