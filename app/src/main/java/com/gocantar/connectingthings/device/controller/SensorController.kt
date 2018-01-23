package com.gocantar.connectingthings.device.controller

import android.bluetooth.BluetoothGatt
import android.os.ParcelUuid
import com.example.gocantar.connectingthings.common.extension.getSensorServiceUuid
import com.example.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.gocantar.connectingthings.device.sensor.WeatherStationArduino101
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorControllerBoundary

/**
 * Created by gocantar on 23/1/18.
 */
class SensorController: TemperatureSensorControllerBoundary {
    override fun enableNotifications(gatt: BluetoothGatt) {
        val service = getServiceUuid(gatt)
        when(service){
            ParcelUuid(ServicesUUIDs.ARDUINO101_WEATHER_STATION_SERVICE) ->
                WeatherStationArduino101.enableNotifications(gatt)
        }
    }

    override fun disableNotifications(gatt: BluetoothGatt) {
        val service = getServiceUuid(gatt)
        when(service){
            ParcelUuid(ServicesUUIDs.ARDUINO101_WEATHER_STATION_SERVICE) ->
                WeatherStationArduino101.disableNotifications(gatt)
        }
    }

    private fun getServiceUuid(gatt: BluetoothGatt): ParcelUuid{
        return gatt.services.map { ParcelUuid(it.uuid) }.getSensorServiceUuid()
    }
}