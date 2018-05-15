package com.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.data.Entry
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.common.Constants
import com.gocantar.connectingthings.common.ids.Key
import com.gocantar.connectingthings.di.component.AppComponent
import com.gocantar.connectingthings.di.component.DaggerWeatherStationComponent
import com.gocantar.connectingthings.di.module.WeatherStationControllerModule
import com.gocantar.connectingthings.domain.entity.BLEDevice
import com.gocantar.connectingthings.domain.entity.HumidityParams
import com.gocantar.connectingthings.domain.entity.TemperatureParams
import com.gocantar.connectingthings.domain.usecase.GetDeviceActor
import com.gocantar.connectingthings.domain.usecase.GetHumidityDataActor
import com.gocantar.connectingthings.domain.usecase.GetTemperatureDataActor
import com.gocantar.connectingthings.domain.usecase.ManageSensorNotificationsActor
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by gocantar on 28/1/18.
 */
class WeatherStationViewModel(app: Application): BaseViewModel(app) {

    private val mDeviceName: MutableLiveData<String> = MutableLiveData()
    private lateinit var mDevice: BLEDevice
    val mTemperatureData: MutableList<Entry>  = mutableListOf()
    val mTemperatureDataChange: MutableLiveData<Boolean> = MutableLiveData()
    val mHumidityData: MutableList<Entry> = mutableListOf()
    val mHumidityDataChange: MutableLiveData<Boolean> = MutableLiveData()


    @Inject lateinit var mGetDevice: GetDeviceActor
    @Inject lateinit var mGetNotifications: ManageSensorNotificationsActor
    @Inject lateinit var mGetTemperatureDataActor: GetTemperatureDataActor
    @Inject lateinit var mGetHumidityDataActor: GetHumidityDataActor

    fun initialize(data: Intent){
        getDevice(data)
    }

    private fun getDevice (data: Intent){
            val address = data.extras.getString(Key.DEVICE_ADDRESS)
            mGetDevice.execute(object : DisposableObserver<BLEDevice>() {
                override fun onComplete() {
                    getSensorData()
                }
                override fun onNext(device: BLEDevice?) {
                    device?.let {
                        mDevice = device
                        setDeviceName(mDevice.name)
                    }?: setDeviceName()
                }
                override fun onError(e: Throwable?) {
                    Log.e(TAG, e.toString())
                }
            }, address)
    }

    fun enableNotifications(){
        mDevice.gattBluetoothGatt?.let { mGetNotifications.enable(it) }
    }

    fun disableNotifications(){
        mDevice.gattBluetoothGatt?.let { mGetNotifications.disable(it) }
    }

    private fun setDeviceName(name: String = mResources.getString(R.string.error)){
        mDeviceName.value = name
    }

   private fun getSensorData(timestamp: Long? = null){
       val params = getParams(mDevice.bluetoothDevice.address, timestamp)
       mTemperatureData.clear()
       mHumidityData.clear()
       getTemperatureData(params)
       getHumidityData(params)
   }

    private fun getParams(address: String, timestamp: Long? = null): Bundle{
        val bundle = Bundle()
        bundle.putString(Constants.ADDRESS, address)
        timestamp?.let {
            bundle.putLong(Constants.TIMESTAMP, timestamp)
        }
        return bundle
    }

    private fun getTemperatureData(params: Bundle){
        mGetTemperatureDataActor.execute(object : DisposableObserver<TemperatureParams>() {
            override fun onComplete() {
                Log.d(TAG, "${mDevice.bluetoothDevice.address}: Temperature data has been loaded")
                mTemperatureDataChange.value = true
            }
            override fun onNext(temperature: TemperatureParams?) {
                temperature?.let { mTemperatureData.add(Entry(mTemperatureData.size.toFloat(), it.value.toFloat())) }
            }
            override fun onError(e: Throwable?) {
                Log.e(TAG, e.toString())
            }
        }, params)
    }

    private fun getHumidityData(params: Bundle){
        mGetHumidityDataActor.execute(object : DisposableObserver<HumidityParams>(){
            override fun onComplete() {
                Log.d(TAG, "${mDevice.bluetoothDevice.address}: Humidity data has been loaded")
                mHumidityDataChange.value = true
            }
            override fun onNext(humidity: HumidityParams?) {
                humidity?.let { mHumidityData.add(Entry(mHumidityData.size.toFloat(), it.value.toFloat())) }
            }
            override fun onError(e: Throwable?) {
                Log.e(TAG, e.toString())
            }
        }, params)
    }

    override fun setUpComponent(appComponent: AppComponent) {
        DaggerWeatherStationComponent.builder()
                .appComponent(appComponent)
                .weatherStationControllerModule(WeatherStationControllerModule(this))
                .build()
                .inject(this)
    }

    override fun onCleared() {
        super.onCleared()
        mGetNotifications.dispose()
        mGetHumidityDataActor.dispose()
        mGetTemperatureDataActor.dispose()
    }
}