package com.gocantar.connectingthings.device.controller

import android.bluetooth.BluetoothGatt
import android.os.ParcelUuid
import com.gocantar.connectingthings.common.extension.getSensorServiceUuid
import com.gocantar.connectingthings.common.extension.getServiceUuidFromCharacteristic
import com.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.gocantar.connectingthings.device.sensor.WeatherStationArduino101
import com.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorControllerBoundary
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.entity.SensorData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by gocantar on 23/1/18.
 */

class SensorController @Inject constructor(private val mBLEController: BLEServiceBoundary) : TemperatureSensorControllerBoundary {

    private val mDisposable: CompositeDisposable = CompositeDisposable()

    private val mDescriptorObservable = getDescriptorObservable()

    override fun enableNotifications(gatt: BluetoothGatt) {
        mDisposable.add(mDescriptorObservable
                .take(1)
                .subscribe {
                    enableHumidityNotifications(gatt)
                }
        )
        enableTemperatureNotifications(gatt)
    }

    override fun disableNotifications(gatt: BluetoothGatt) {
        mDisposable.add(mDescriptorObservable
                .take(1)
                .subscribe {
                    disableHumidityNotifications(gatt)
                })
        disableTemperatureNotifications(gatt)
    }

    override fun clearDisposables() {
        mDisposable.clear().takeUnless { mDisposable.isDisposed }
    }

    override fun decode(charData: CharacteristicData): SensorData? {
        val service = charData.uuid.getServiceUuidFromCharacteristic()
        return when(service){
            ServicesUUIDs.ARDUINO101_WEATHER_STATION_SERVICE -> {
                WeatherStationArduino101.decodeData(charData)
            }
            else -> null
        }
    }

    /**
     * Private fun
     */

    private fun enableTemperatureNotifications(gatt: BluetoothGatt) {
        val service = getServiceUuid(gatt)
        when(service){
            ParcelUuid(ServicesUUIDs.ARDUINO101_WEATHER_STATION_SERVICE) ->
                WeatherStationArduino101.enableTemperatureNotification(gatt)
        }
    }

    private fun enableHumidityNotifications(gatt: BluetoothGatt) {
        val service = getServiceUuid(gatt)
        when(service){
            ParcelUuid(ServicesUUIDs.ARDUINO101_WEATHER_STATION_SERVICE) ->
                WeatherStationArduino101.enableHumidityNotification(gatt)
        }
    }

    private fun disableTemperatureNotifications(gatt: BluetoothGatt) {
        val service = getServiceUuid(gatt)
        when(service){
            ParcelUuid(ServicesUUIDs.ARDUINO101_WEATHER_STATION_SERVICE) ->
                WeatherStationArduino101.disableTemperatureNotification(gatt)
        }
    }

    private fun disableHumidityNotifications(gatt: BluetoothGatt) {
        val service = getServiceUuid(gatt)
        when(service){
            ParcelUuid(ServicesUUIDs.ARDUINO101_WEATHER_STATION_SERVICE) ->
                WeatherStationArduino101.disableHumidityNotification(gatt)
        }
    }

    private fun getDescriptorObservable(): Observable<Int>{
        return mBLEController.mPublisherDescriptor
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
    }

    private fun getServiceUuid(gatt: BluetoothGatt): ParcelUuid{
        return gatt.services.map { ParcelUuid(it.uuid) }.getSensorServiceUuid()
    }
}