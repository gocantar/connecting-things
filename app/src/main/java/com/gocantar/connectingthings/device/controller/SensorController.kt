package com.gocantar.connectingthings.device.controller

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattDescriptor
import android.os.ParcelUuid
import com.gocantar.connectingthings.common.enum.State
import com.gocantar.connectingthings.common.extension.getSensorServiceUuid
import com.gocantar.connectingthings.device.sensor.WeatherStationFactory
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

    // TODO Value of enable notifications indicate if notifications are disabled or enabled
    override fun enableNotifications(gatt: BluetoothGatt) {
        mDisposable.add(mDescriptorObservable
                .firstElement()
                .subscribe {
                    enableHumidityNotifications(gatt)
                }
        )
        enableTemperatureNotifications(gatt)
    }

    override fun disableNotifications(gatt: BluetoothGatt) {
        mDisposable.add(mDescriptorObservable
                .firstElement()
                .subscribe {
                    disableHumidityNotifications(gatt)
                })
        disableTemperatureNotifications(gatt)
    }

    override fun getNotificationState(): Observable<State> {
        return  mDescriptorObservable
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

    override fun clearDisposables() {
        mDisposable.clear().takeUnless { mDisposable.isDisposed }
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