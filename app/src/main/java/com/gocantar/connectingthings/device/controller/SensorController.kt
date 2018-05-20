package com.gocantar.connectingthings.device.controller

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattDescriptor
import android.os.ParcelUuid
import android.util.Log
import com.gocantar.connectingthings.common.enum.State
import com.gocantar.connectingthings.common.extension.getSensorServiceUuid
import com.gocantar.connectingthings.device.sensor.WeatherStationFactory
import com.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorControllerBoundary
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.entity.SensorData
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by gocantar on 23/1/18.
 */

class SensorController @Inject constructor(private val mBLEController: BLEServiceBoundary) : TemperatureSensorControllerBoundary {




    // TODO Value of enable notifications indicate if notifications are disabled or enabled
    override fun enableNotifications(gatt: BluetoothGatt) {
        mBLEController.mPublisherDescriptorWritten
                .observeOn(Schedulers.io())
                .doOnSubscribe({ enableTemperatureNotifications(gatt) })
                .firstElement()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    enableHumidityNotifications(gatt)
                }
    }

    override fun disableNotifications(gatt: BluetoothGatt) {
        mBLEController.mPublisherDescriptorWritten
                .observeOn(Schedulers.io())
                .doOnSubscribe({ disableTemperatureNotifications(gatt) })
                .firstElement()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    disableHumidityNotifications(gatt)
                }
    }

    override fun getNotificationState(): Observable<State> {
        return  getDescriptorObservable()
                .map {
                    when{
                        it.contentEquals(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE) -> State.AVAILABLE
                        else -> State.DISABLE
                    }
                }
    }

    override fun requestNotificationsState(gatt: BluetoothGatt){
        WeatherStationFactory.createWeatherStation(getServiceUuid(gatt)).requestNotificationsState(gatt)
    }


    override fun decode(charData: CharacteristicData): SensorData? =
        WeatherStationFactory.createWeatherStation(ParcelUuid(charData.uuid)).decodeData(charData)


    /**
     * Private fun
     */

    private fun enableTemperatureNotifications(gatt: BluetoothGatt) {
        WeatherStationFactory.createWeatherStation(getServiceUuid(gatt)).enableTemperatureNotification(gatt)
    }

    private fun enableHumidityNotifications(gatt: BluetoothGatt) {
        WeatherStationFactory.createWeatherStation(getServiceUuid(gatt)).enableHumidityNotification(gatt)
    }

    private fun disableTemperatureNotifications(gatt: BluetoothGatt) {
        WeatherStationFactory.createWeatherStation(getServiceUuid(gatt)).disableTemperatureNotification(gatt)
    }

    private fun disableHumidityNotifications(gatt: BluetoothGatt) {
        WeatherStationFactory.createWeatherStation(getServiceUuid(gatt)).disableHumidityNotification(gatt)
    }

    private fun getDescriptorObservable(): Observable<ByteArray>{
        return mBLEController.mPublisherDescriptor
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
    }

    private fun getServiceUuid(gatt: BluetoothGatt): ParcelUuid{
        return gatt.services.map { ParcelUuid(it.uuid) }.getSensorServiceUuid()
    }
}