package com.gocantar.connectingthings.device.sensor

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import com.gocantar.connectingthings.common.ids.CharacteristicUUIDs
import com.gocantar.connectingthings.common.ids.ServicesUUIDs

/**
 * Created by gocantar on 23/1/18.
 */
object WeatherStationArduino101 {

    fun enableNotifications(gatt: BluetoothGatt){
        enableTemperatureNotification(gatt)
        enableHumidityNotification(gatt)
    }

    fun disableNotifications(gatt: BluetoothGatt){
        disableTemperatureNotification(gatt)
        disableHumidityNotification(gatt)
    }

    /**
     * Private functions
     */
    private fun enableTemperatureNotification(gatt: BluetoothGatt){
        val characteristic = getTemperatureCharacteristic(gatt)
        gatt.setCharacteristicNotification(characteristic, true)
        writeConfigCharacteristicDescriptor(gatt, characteristic, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
    }

    private fun disableTemperatureNotification(gatt: BluetoothGatt){
        val characteristic = getTemperatureCharacteristic(gatt)
        gatt.setCharacteristicNotification(characteristic, false)
        writeConfigCharacteristicDescriptor(gatt, characteristic, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)
    }

    private fun enableHumidityNotification(gatt: BluetoothGatt){
        val characteristic = getHumidityCharacteristic(gatt)
        gatt.setCharacteristicNotification(characteristic, true)
        writeConfigCharacteristicDescriptor(gatt, characteristic, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
    }

    private fun disableHumidityNotification(gatt: BluetoothGatt){
        val characteristic = getHumidityCharacteristic(gatt)
        gatt.setCharacteristicNotification(characteristic, false)
        writeConfigCharacteristicDescriptor(gatt, characteristic, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)
    }

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