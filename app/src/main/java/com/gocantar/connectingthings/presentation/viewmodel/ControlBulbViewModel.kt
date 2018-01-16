package com.example.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.common.Constants
import com.example.gocantar.connectingthings.common.enum.State
import com.example.gocantar.connectingthings.di.component.AppComponent
import com.example.gocantar.connectingthings.di.component.DaggerBulbControllerComponent
import com.example.gocantar.connectingthings.di.module.BulbControllerModule
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.domain.entity.BulbParams
import com.example.gocantar.connectingthings.domain.entity.BulbStatus
import com.example.gocantar.connectingthings.domain.entity.CharacteristicData
import com.example.gocantar.connectingthings.domain.usecase.*
import com.example.gocantar.connectingthings.presentation.model.BulbColor
import com.example.gocantar.connectingthings.presentation.model.BulbEffect
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by gocantar on 14/11/17.
 */

class ControlBulbViewModel(app: Application): BaseViewModel(app){

    val UPDATE_ALL_RECYCLER = -1

    val mEffectsList: List<BulbEffect> by lazy {
        mResources.getStringArray(R.array.effects).asList()
                .map { BulbEffect(it, State.AVAILABLE) }
    }

    val mColorList: List<BulbColor> by lazy {
        mResources.getIntArray(R.array.bulb_colors_palette).asList()
                .map { BulbColor(it, State.DISABLE) }
    }


    var mEffect: Int = getEffectIdFromString(mEffectsList.first().effect)
    var mColor: Int = mResources.getColor(R.color.white, app.theme)
    var mAlpha: Int = 0

    lateinit var mDevice: BLEDevice

    @Inject lateinit var mGetDeviceActor: GetDeviceActor
    @Inject lateinit var mSetColorActor: SetBulbColorActor
    @Inject lateinit var mGetNotificationsActor: GetCharacteristicNotificationActor
    @Inject lateinit var mReadBulbCharacteristicActor: ReadBulbStateActor
    @Inject lateinit var mDecodeStatusCharacteristic: DecodeBulbCharacteristicActor

    private var mGetDeviceDisposable: DisposableObserver<BLEDevice> = object: DisposableObserver<BLEDevice>() {
        override fun onError(e: Throwable?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
        override fun onNext(device: BLEDevice?) {
            device?.let {
                mDevice = device
            }
            ba_title.value = device?.name ?: mResources.getString(R.string.error)
        }
        override fun onComplete() {
            Log.d(TAG, "Device connected was gotten")
            // Read value
           readCharacteristic()
        }
    }
    private var mNotificationDisposable: DisposableObserver<CharacteristicData> = object : DisposableObserver<CharacteristicData>() {
        override fun onComplete() {
            // Never it´ called
        }
        override fun onNext(char: CharacteristicData) {
            decodeStatus(char)
        }
        override fun onError(e: Throwable?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
    private var mDecodeStatusDisposable: DisposableObserver<BulbStatus> = object : DisposableObserver<BulbStatus>() {
        override fun onComplete() {
            // Never it's called
        }
        override fun onNext(status: BulbStatus?) {
            when(status){
                null -> TODO("Show error")
                else -> selectEffect(status.effectID)
            }
        }
        override fun onError(e: Throwable?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    /**
     * Data binding variables
     */
    val ba_title: MutableLiveData<String> = MutableLiveData()

    val mLoadingData: MutableLiveData<Boolean> = MutableLiveData()

    val mEffectsRecycler: MutableLiveData<Int> = MutableLiveData()
    val mColorsRecycler: MutableLiveData<Int> = MutableLiveData()

    /**
     * ---------------------------------------------------------
     */

    fun initialize(address: String){
        mGetNotificationsActor.execute(mNotificationDisposable, Unit)
        mGetDeviceActor.execute(mGetDeviceDisposable, address)
        mLoadingData.value = true
    }

    fun putColor(color: Int){
        when{
            mColor != color -> {
                selectColor(color)
                mSetColorActor.execute(BulbParams (mDevice,
                        BulbStatus(true, mColor, mAlpha, mEffect)))
            }
        }
    }

    fun putEffect(effect: String){
        val effectId = getEffectIdFromString(effect)
        when{
            mEffect !=  effectId -> {
                selectEffect(effectId)
            }
        }
    }

    private fun readCharacteristic(){
        when{
            mDevice.gattBluetoothGatt != null -> mReadBulbCharacteristicActor
                    .execute(mDevice.gattBluetoothGatt!!)
            else -> TODO("Show error BLE device error")
        }
    }

    private fun decodeStatus(data: CharacteristicData){
        when{
            mDevice.gattBluetoothGatt != null -> mDecodeStatusCharacteristic
                    .execute(mDecodeStatusDisposable, mDevice.gattBluetoothGatt!!, data)

            else -> TODO("Show error BLE device error")
        }
    }

    private fun selectEffect(effectId: Int){
        mEffectsList[mEffect].state = State.AVAILABLE
        mEffectsRecycler.value = mEffect

        mEffect = effectId
        mEffectsList[effectId].state = State.SELECTED
        mEffectsRecycler.value = effectId

        when(effectId){
            Constants.COLOR_EFFECT, Constants.PULSE_EFFECT,
            Constants.FADE_EFFECT, Constants.CANDLE_EFFECT -> {
                selectColor(mColor)
            }
            else -> disableALlColors()
        }
        mLoadingData.value = false
    }

    private fun selectColor(color: Int){
        mColor = color
        mColorList.forEach {
            when(mColor){
                it.color ->  mColorList[mColorList.indexOf(it)].state = State.SELECTED
                else ->  mColorList[mColorList.indexOf(it)].state = State.AVAILABLE
            }
        }
        mColorsRecycler.value = UPDATE_ALL_RECYCLER
    }

    private fun disableALlColors(){
        mColorList.forEach { mColorList[mColorList.indexOf(it)].state = State.DISABLE }
        mColorsRecycler.value = UPDATE_ALL_RECYCLER
    }

    private fun getEffectIdFromString(effect: String): Int {
        return when (effect) {
            mEffectsList[0].effect -> Constants.COLOR_EFFECT
            mEffectsList[1].effect -> Constants.CANDLE_EFFECT
            mEffectsList[2].effect -> Constants.FADE_EFFECT
            mEffectsList[3].effect -> Constants.PULSE_EFFECT
            mEffectsList[4].effect -> Constants.DECREASE_EFFECT
            mEffectsList[5].effect -> Constants.RAINBOW_EFFECT
            else -> Constants.COLOR_EFFECT
        }
    }

    override fun onCleared() {
        super.onCleared()
        mGetDeviceActor.dispose()
        mGetNotificationsActor.dispose()
    }

    override fun setUpComponent(appComponent: AppComponent) {
        DaggerBulbControllerComponent.builder()
                .appComponent(appComponent)
                .bulbControllerModule(BulbControllerModule(this))
                .build()
                .inject(this)
    }

}