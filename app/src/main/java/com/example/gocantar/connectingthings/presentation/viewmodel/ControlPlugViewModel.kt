package com.example.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
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

    /**
     * Private val
     */

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
                            mTitle.value = mDevice.name
                        }
                        else -> mTitle.value = mResources.getString(R.string.error)
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
                    val consumption = mDecodeLivePowerConsumptionActor
                                        .decode(mDevice.gattBluetoothGatt!!, t)
                    mPowerConsumption.value = String.format(mResources.getString(R.string.power_consumption), consumption/1000)
                }
            }

    private lateinit var mDevice: BLEDevice
    private val STATE_OFF = Pair(mResources.getColor(R.color.redWrong, app.theme), mResources.getString(R.string.off))
    private val STATE_ON = Pair(mResources.getColor(R.color.greenOK, app.theme), mResources.getString(R.string.on))

    /**
     * Case uses
     */
    @Inject lateinit var mGetDeviceActor: GetDeviceActor
    @Inject lateinit var mSetStatusActor: SetPlugStateActor
    @Inject lateinit var mManageNotificationsActor: ManagePlugNotificationsActor
    @Inject lateinit var mGetNotificationsActor: GetCharacteristicNotificationActor
    @Inject lateinit var mRequestLiveConsumptionActor: RequestLivePowerConsumptionActor
    @Inject lateinit var mDecodeLivePowerConsumptionActor: DecodeLivePowerConsumptionActor

    /**
     * Data binding variables
     */
    val mTitle: MutableLiveData<String> = MutableLiveData()
    val mPowerConsumption: MutableLiveData<String> = MutableLiveData()
    val mPowerConsumptionProgress: MutableLiveData<Int> = MutableLiveData()
    val mPlugState: MutableLiveData<Pair<Int, String>> = MutableLiveData()

    /**
     * ---------------------------------------------------------
     */

    init {
        mPowerConsumption.value = String.format(mResources.getString(R.string.power_consumption), 0)
        mPowerConsumptionProgress.value = 0
        mPlugState.value = STATE_ON
    }

    fun initialize(address: String){
        mGetDeviceActor.execute(mGetDeviceDisposable, address)
    }

    fun turnOn() = mSetStatusActor.turnOn(mDevice.gattBluetoothGatt!!)

    fun turnOff() = mSetStatusActor.turnOff(mDevice.gattBluetoothGatt!!)

    private fun enableNotifications(){
        mDevice.gattBluetoothGatt?.let {
            mManageNotificationsActor.enable(it)
            mGetNotificationsActor.execute(mGetNotificationsDisposable, Unit)
            mRequestLiveConsumptionActor.execute(it)
        }
    }

    private fun disableNotifications(){
        mDevice.gattBluetoothGatt?.let {
            mManageNotificationsActor.disable(it)
        }
    }

    override fun onCleared() {
        mGetDeviceActor.dispose()
        mGetNotificationsActor.dispose()
        mRequestLiveConsumptionActor.stop()
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