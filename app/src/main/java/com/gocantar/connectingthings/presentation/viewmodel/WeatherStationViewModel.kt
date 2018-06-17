package com.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.data.Entry
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.common.Constants
import com.gocantar.connectingthings.common.enum.State
import com.gocantar.connectingthings.common.ids.Key
import com.gocantar.connectingthings.di.component.AppComponent
import com.gocantar.connectingthings.di.component.DaggerWeatherStationComponent
import com.gocantar.connectingthings.di.module.WeatherStationControllerModule
import com.gocantar.connectingthings.domain.entity.BLEDevice
import com.gocantar.connectingthings.domain.entity.HumidityParams
import com.gocantar.connectingthings.domain.entity.TemperatureParams
import com.gocantar.connectingthings.domain.usecase.*
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by gocantar on 28/1/18.
 */
class WeatherStationViewModel(app: Application): BaseViewModel(app) {

    val mDeviceName: MutableLiveData<String> = MutableLiveData()
    private lateinit var mDevice: BLEDevice
    val mTemperatureData: MutableList<Entry>  = mutableListOf()
    val mTemperatureDataChange: MutableLiveData<Boolean> = MutableLiveData()
    val mHumidityData: MutableList<Entry> = mutableListOf()
    val mHumidityDataChange: MutableLiveData<Boolean> = MutableLiveData()
    val mNotificationsState: MutableLiveData<State> = MutableLiveData()


    @Inject lateinit var mGetDevice: GetDeviceActor
    @Inject lateinit var mGetNotifications: ManageSensorNotificationsActor
    @Inject lateinit var mGetTemperatureDataActor: GetTemperatureDataActor
    @Inject lateinit var mGetHumidityDataActor: GetHumidityDataActor
    @Inject lateinit var mGetDescriptorValueActor: GetDescriptorValueActor


    fun initialize(data: Intent){
        getDevice(data)
    }

    private fun getDevice (data: Intent){
            val address = data.extras.getString(Key.DEVICE_ADDRESS)
            mGetDevice.execute(object : DisposableObserver<BLEDevice>() {
                override fun onComplete() {
                    requestNotificationState()
                    getSensorData()
                }
                override fun onNext(device: BLEDevice?) {
                    device?.let {
                        mDevice = device
                        setDeviceName(mDevice.name)
                    }?: setDeviceName()
                }
                override fun onError(e: Throwable?) {
                    mErrorSnackbar.value = mResources.getString(R.string.error_device)
                }
            }, address)
    }

    fun enableNotifications() = mDevice.gattBluetoothGatt?.let { mGetNotifications.enable(it) }


    fun disableNotifications() = mDevice.gattBluetoothGatt?.let { mGetNotifications.disable(it) }

    private fun setDeviceName(name: String = mResources.getString(R.string.error)){
        mDeviceName.value = name
    }

    fun getSensorData(timestamp: Long? = null){
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
                Log.d(TAG, "${mDevice.bluetoothDevice.address}: Has been received ${mTemperatureData.size} values of temperature")
                if (mTemperatureData.isNotEmpty()){
                    mTemperatureDataChange.value = true
                }
            }
            override fun onNext(temperature: TemperatureParams?) {
                temperature?.let { mTemperatureData.add(Entry(mTemperatureData.size.toFloat(), it.value.toFloat())) }
            }
            override fun onError(e: Throwable?) {
                mErrorSnackbar.value = mResources.getString(R.string.error_data)
            }
        }, params)
    }

    private fun getHumidityData(params: Bundle){
        mGetHumidityDataActor.execute(object : DisposableObserver<HumidityParams>(){
            override fun onComplete() {
                Log.d(TAG, "${mDevice.bluetoothDevice.address}: Has been received ${mHumidityData.size} values of humidity")
                if (mHumidityData.isNotEmpty()){
                    mHumidityDataChange.value = true
                }
            }
            override fun onNext(humidity: HumidityParams?) {
                humidity?.let { mHumidityData.add(Entry(mHumidityData.size.toFloat(), it.value.toFloat())) }
            }
            override fun onError(e: Throwable?) {
                mErrorSnackbar.value = mResources.getString(R.string.error_data)
            }
        }, params)
    }

    private fun requestNotificationState(){
        mDevice.gattBluetoothGatt?.let {
            mGetDescriptorValueActor.execute(object : DisposableObserver<State>() {
                override fun onComplete() {
                    // Never it's called
                }

                override fun onNext(state: State) {
                    Log.d(TAG, "Notifications state: $state")
                    mNotificationsState.value = state
                }

                override fun onError(e: Throwable?) {
                    mErrorSnackbar.value = mResources.getString(R.string.error_notifications)
                }
            }, it)
        }
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
        mGetHumidityDataActor.dispose()
        mGetTemperatureDataActor.dispose()
        mGetDescriptorValueActor.dispose()
    }
}