package com.example.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.bluetooth.BluetoothGatt
import android.util.Log
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.di.component.AppComponent
import com.example.gocantar.connectingthings.di.component.DaggerPlugControllerComponent
import com.example.gocantar.connectingthings.di.module.PlugControllerModule
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.domain.entity.CharacteristicData
import com.example.gocantar.connectingthings.domain.usecase.*
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by gocantar on 29/11/17.
 */
class ControlPlugViewModel(app: Application): BaseViewModel(app) {

    lateinit var mDevice: BLEDevice
    private var mGetDeviceDisposable: DisposableObserver<BLEDevice> =
            object : DisposableObserver<BLEDevice>() {
                override fun onComplete() {
                    Log.d(TAG, "Device connected was gotten")
                    enableNotifications()
                }
                override fun onError(error: Throwable?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
                override fun onNext(device: BLEDevice?) {
                    when{
                        device?.gattBluetoothGatt != null -> {
                            mDevice = device
                            pa_title.value = mDevice.name
                        }
                        else -> pa_title.value = mResources.getString(R.string.error)
                    }
                }
            }
    private var mGetNotificationsDisposable: DisposableObserver<CharacteristicData> =
            object : DisposableObserver<CharacteristicData>() {
                override fun onError(e: Throwable?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onComplete() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNext(t: CharacteristicData) {
                    Log.d(TAG, "PowerConsumption has been received")
                }
            }

    /**
     * Case uses
     */
    @Inject lateinit var mGetDeviceActor: GetDeviceActor
    @Inject lateinit var mSetStatusActor: SetPlugStatusActor
    @Inject lateinit var mManageNotifications: ManagePlugNotificationsActor
    @Inject lateinit var mGetNotifications: GetCharacteristicNotificationActor
    @Inject lateinit var mRequestLiveConsumption: RequestLivePowerConsumptionActor

    /**
     * Data binding variables
     */
    val pa_title: MutableLiveData<String> = MutableLiveData()
    val mLoadingInformation: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * ---------------------------------------------------------
     */

    fun initialize(address: String){
        mLoadingInformation.value = true
        mGetDeviceActor.execute(mGetDeviceDisposable, address)
    }

    private fun enableNotifications(){
        mDevice.gattBluetoothGatt?.let {
            mManageNotifications.enable(it)
            mGetNotifications.execute(mGetNotificationsDisposable, Unit)
            mRequestLiveConsumption.execute(it)
        }
    }

    private fun disableNotifications(){
        mDevice.gattBluetoothGatt?.let {
            mManageNotifications.disable(it)
        }
    }

    override fun onCleared() {
        mGetDeviceActor.dispose()
        mGetNotifications.dispose()
        mRequestLiveConsumption.stop()
        disableNotifications()
    }

    override fun setUpComponent(appComponent: AppComponent) {
        DaggerPlugControllerComponent.builder()
                .appComponent(appComponent)
                .plugControllerModule(PlugControllerModule(this))
                .build()
                .inject(this)
    }
}