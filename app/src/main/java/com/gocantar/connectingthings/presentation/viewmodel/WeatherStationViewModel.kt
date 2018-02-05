package com.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.common.ids.Key
import com.gocantar.connectingthings.di.component.AppComponent
import com.gocantar.connectingthings.di.component.DaggerWeatherStationComponent
import com.gocantar.connectingthings.di.module.WeatherStationControllerModule
import com.gocantar.connectingthings.domain.entity.BLEDevice
import com.gocantar.connectingthings.domain.usecase.GetDeviceActor
import com.gocantar.connectingthings.domain.usecase.ManageSensorNotificationsActor
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by gocantar on 28/1/18.
 */
class WeatherStationViewModel(app: Application): BaseViewModel(app) {

    private val mDeviceName: MutableLiveData<String> = MutableLiveData()
    private lateinit var mDevice: BLEDevice

    @Inject lateinit var mGetDevice: GetDeviceActor
    @Inject lateinit var mGetNotifications: ManageSensorNotificationsActor

    fun initialize(data: Intent){
        getDevice(data)
    }

    private fun getDevice (data: Intent){
            val address = data.extras.getString(Key.DEVICE_ADDRESS)
            mGetDevice.execute(object : DisposableObserver<BLEDevice>() {
                override fun onComplete() {
                    // Nothing to do
                }
                override fun onNext(device: BLEDevice?) {
                    device?.let {
                        mDevice = device
                        setDeviceName(mDevice.name)
                    }?: setDeviceName()
                }
                override fun onError(e: Throwable?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
    }
}