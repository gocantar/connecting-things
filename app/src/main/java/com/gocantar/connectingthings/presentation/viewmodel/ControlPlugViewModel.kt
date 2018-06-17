package com.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.common.Constants
import com.gocantar.connectingthings.common.enum.State
import com.gocantar.connectingthings.di.component.AppComponent
import com.gocantar.connectingthings.di.component.DaggerPlugControllerComponent
import com.gocantar.connectingthings.di.module.PlugControllerModule
import com.gocantar.connectingthings.domain.entity.BLEDevice
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.usecase.*
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
                    mErrorSnackbar.value = mResources.getString(R.string.error_device)
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
                    mErrorSnackbar.value = mResources.getString(R.string.error_notifications)
                }

                override fun onComplete() {
                    // Never it's called
                }

                override fun onNext(data: CharacteristicData) {
                    Log.d(TAG, "PowerConsumption has been received")
                    val dataDecoded: Pair<Int, State> = mDecodeLivePowerConsumptionActor
                                        .decode(mDevice.gattBluetoothGatt!!, data)
                    mPowerConsumption.value = String.format(mResources.getString(R.string.power_consumption), getWattsFromInt(dataDecoded.first))
                    mPowerConsumptionProgress.value = getProgress(dataDecoded.first)
                    mPlugState.value = when(dataDecoded.second){
                        State.AVAILABLE -> STATE_ON
                        else -> STATE_OFF
                    }
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

    fun turnOn() {
        mSetStatusActor.turnOn(mDevice.gattBluetoothGatt!!)
        mPlugState.value = STATE_ON
    }

    fun turnOff(){
        mSetStatusActor.turnOff(mDevice.gattBluetoothGatt!!)
        mPlugState.value = STATE_OFF
    }

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

    private fun getWattsFromInt(intValue: Int): String{
        var value = intValue
        if(intValue <= 0){
            value = 0
        }
        return String.format("%.2f", value/1000.toFloat())
    }

    private fun getProgress(value: Int): Int{

        val wattsConsumption = when{
            value < 0 -> Constants.MIN_CONSUMPTION_LOW
            else -> value/1000
        }

        return when(wattsConsumption){
            in Constants.MIN_CONSUMPTION_LOW..Constants.MAX_CONSUMPTION_LOW ->
                wattsConsumption
            in Constants.MIN_CONSUMPTION_MEDIUM..Constants.MAX_CONSUMPTION_MEDIUM ->
                getProgressValue(wattsConsumption, Constants.MIN_CONSUMPTION_MEDIUM, Constants.MAX_CONSUMPTION_MEDIUM, 300 )
            in Constants.MIN_CONSUMPTION_UPPER_MEDIUM..Constants.MAX_CONSUMPTION_UPPER_MEDIUM ->
                getProgressValue(wattsConsumption, Constants.MIN_CONSUMPTION_UPPER_MEDIUM, Constants.MAX_CONSUMPTION_UPPER_MEDIUM, 540)
            in Constants.MIN_CONSUMPTION_HIGH..Constants.MAX_CONSUMPTION_HIGH ->
                getProgressValue(wattsConsumption, Constants.MIN_CONSUMPTION_HIGH, Constants.MAX_CONSUMPTION_HIGH, 750)
            else -> 1000
        }
    }

    private fun getProgressValue(value: Int, min: Int, max: Int, prevProgress: Int): Int {
        val progress =  prevProgress + (((value - min) * min) / (max - min))
        return when (progress > max) {
            true -> max
            else -> progress
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