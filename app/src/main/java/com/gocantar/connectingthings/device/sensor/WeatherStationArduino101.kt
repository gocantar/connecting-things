package com.gocantar.connectingthings.device.sensor

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import com.gocantar.connectingthings.common.enum.SensorType
import com.gocantar.connectingthings.common.ids.CharacteristicUUIDs
import com.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.entity.SensorData

/**
 * Created by gocantar on 23/1/18.
 */
class WeatherStationArduino101 :WeatherStation {


    override fun enableTemperatureNotification(gatt: BluetoothGatt){
        val characteristic = getTemperatureCharacteristic(gatt)
        gatt.setCharacteristicNotification(characteristic, true)
        writeConfigCharacteristicDescriptor(gatt, characteristic, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
    }

    override fun disableTemperatureNotification(gatt: BluetoothGatt){
        val characteristic = getTemperatureCharacteristic(gatt)
        gatt.setCharacteristicNotification(characteristic, false)
        writeConfigCharacteristicDescriptor(gatt, characteristic, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)
    }

    override fun enableHumidityNotification(gatt: BluetoothGatt){
        val characteristic = getHumidityCharacteristic(gatt)
        gatt.setCharacteristicNotification(characteristic, true)
        writeConfigCharacteristicDescriptor(gatt, characteristic, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
    }

    override fun disableHumidityNotification(gatt: BluetoothGatt){
        val characteristic = getHumidityCharacteristic(gatt)
        gatt.setCharacteristicNotification(characteristic, false)
        writeConfigCharacteristicDescriptor(gatt, characteristic, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)
    }

    override fun requestNotificationsState(gatt: BluetoothGatt) {
        val characteristic = getTemperatureCharacteristic(gatt)
        gatt.readDescriptor(characteristic.getDescriptor(CharacteristicUUIDs.DESCTRIPTOR_CONFIG_CHARACTERISTIC))
    }

    override fun decodeData(data: CharacteristicData): SensorData{
        val value: Int = data.value.first().toInt()
        return when(data.uuid){
            CharacteristicUUIDs.ARDUINO101_TEMPERATURE -> SensorData(value, data.address, SensorType.TEMPERATURE)
            else -> SensorData(value, data.address, SensorType.HUMIDITY)
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