package com.gocantar.connectingthings.device.sensor

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.entity.SensorData

/**
 * Created by gocantar on 11/3/18.
 */
interface WeatherStation {
    fun enableTemperatureNotification(gatt: BluetoothGatt)
    fun disableTemperatureNotification(gatt: BluetoothGatt)
    fun enableHumidityNotification(gatt: BluetoothGatt)
    fun requestNotificationsState(gatt: BluetoothGatt)
    fun disableHumidityNotification(gatt: BluetoothGatt)
    fun decodeData(data: CharacteristicData): SensorData
}