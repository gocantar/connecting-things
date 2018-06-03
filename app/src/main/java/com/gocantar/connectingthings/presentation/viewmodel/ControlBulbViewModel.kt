package com.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.common.Constants
import com.gocantar.connectingthings.common.enum.State
import com.gocantar.connectingthings.di.component.AppComponent
import com.gocantar.connectingthings.di.component.DaggerBulbControllerComponent
import com.gocantar.connectingthings.di.module.BulbControllerModule
import com.gocantar.connectingthings.domain.entity.BLEDevice
import com.gocantar.connectingthings.domain.entity.BulbParams
import com.gocantar.connectingthings.domain.entity.BulbStatus
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.usecase.*
import com.gocantar.connectingthings.presentation.model.BulbColor
import com.gocantar.connectingthings.presentation.model.BulbEffect
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by gocantar on 14/11/17.
 */

class ControlBulbViewModel(app: Application): BaseViewModel(app){

    val UPDATE_ALL_RECYCLER = -1

    val mEffectsList: List<BulbEffect> by lazy {
        mResources.getStringArray(R.array.effects).asList()
                .map { BulbEffect(it, State.DISABLE) }
    }

    val mColorList: List<BulbColor> by lazy {
        mResources.getIntArray(R.array.bulb_colors_palette).asList()
                .map { BulbColor(it, State.DISABLE) }
    }

    var mEffect: Int = getEffectIdFromString(mEffectsList.first().effect)
    var mColor: Int = mResources.getColor(R.color.white, app.theme)
    var mAlpha: Int = 0x00

    lateinit var mDevice: BLEDevice

    @Inject lateinit var mGetDeviceActor: GetDeviceActor
    @Inject lateinit var mSetColorActor: SetBulbColorActor
    @Inject lateinit var mGetNotificationsActor: GetCharacteristicReadActor
    @Inject lateinit var mReadBulbCharacteristicActor: ReadBulbStateActor
    @Inject lateinit var mDecodeStatusCharacteristic: DecodeBulbCharacteristicActor
    @Inject lateinit var mGetAvailableBulbEffects: GetAvailableBulbEffects

    private val mGetDeviceDisposable: DisposableObserver<BLEDevice> = object: DisposableObserver<BLEDevice>() {
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
            getAvailableEffects()
        }
    }
    private val mNotificationDisposable: DisposableObserver<CharacteristicData> = object : DisposableObserver<CharacteristicData>() {
        override fun onComplete() {
            // Never it´ called
        }
        override fun onNext(char: CharacteristicData) {
            decodeStatus(char)
        }
        override fun onError(e: Throwable?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
    private val mDecodeStatusDisposable: DisposableObserver<BulbStatus> = object : DisposableObserver<BulbStatus>() {
        override fun onComplete() {
            // Never it's called
        }
        override fun onNext(status: BulbStatus?) {
            status?.let {
                selectEffect(it.effectID, it.color, it.period)
            }
        }
        override fun onError(e: Throwable?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    private var mGetAvailableEffectDisposable: DisposableObserver<Int> = object : DisposableObserver<Int>() {
        override fun onComplete() {
            //Nothing TODO
        }
        override fun onNext(effectID: Int) {
            setEffectAsAvailable(effectID)
        }
        override fun onError(e: Throwable?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    /**
     * Data binding variables
     */
    val ba_title: MutableLiveData<String> = MutableLiveData()

    val mEffectsRecycler: MutableLiveData<Int> = MutableLiveData()
    val mColorsRecycler: MutableLiveData<Int> = MutableLiveData()
    val mPeriod: MutableLiveData<Int> = MutableLiveData()
    val mSeekBarVisibility: MutableLiveData<Boolean> = MutableLiveData()


    /**
     * ---------------------------------------------------------
     */

    fun initialize(address: String){
        mGetNotificationsActor.execute(mNotificationDisposable, Unit)
        mGetDeviceActor.execute(mGetDeviceDisposable, address)
    }

    fun onVelocityEffectChanged(velocity: Int){
        mPeriod.value = velocity
        sendDataToDevice()
    }

    fun putColor(color: Int){
        when{
            mColor != color -> {
                selectColor(color)
                sendDataToDevice()
            }
        }
    }

    fun putEffect(effect: Int){
        when{
            mEffect !=  effect -> {
                selectEffect(effectId = effect, period = mPeriod.value?:0)
                sendDataToDevice()
            }
        }
    }

    private fun readCharacteristic(){
        when{
            mDevice.gattBluetoothGatt != null -> mReadBulbCharacteristicActor
                    .execute(mDevice.gattBluetoothGatt!!)
        }
    }

    private fun decodeStatus(data: CharacteristicData){
        when{
            mDevice.gattBluetoothGatt != null -> mDecodeStatusCharacteristic
                    .execute(mDecodeStatusDisposable, mDevice.gattBluetoothGatt!!, data)
        }
    }

    private fun getAvailableEffects(){
        when{
            mDevice.gattBluetoothGatt != null -> mGetAvailableBulbEffects
                    .execute(mGetAvailableEffectDisposable, mDevice.gattBluetoothGatt!!)
        }
    }

    private fun selectEffect(effectId: Int, color: Int = mColor, period: Int){
        mEffectsList[mEffect].state = State.AVAILABLE
        mEffectsRecycler.value = mEffect

        mEffect = effectId
        mEffectsList[effectId].state = State.SELECTED
        mEffectsRecycler.value = effectId

        when(effectId){
            Constants.COLOR_EFFECT -> selectColor(color)
            Constants.PULSE_EFFECT, Constants.FADE_EFFECT,
            Constants.CANDLE_EFFECT -> {
                selectColor(color)
            }
            else -> disableALlColors()
        }
        setPeriodEffect(effectId, period)
    }

    private fun setPeriodEffect(effectId: Int, period: Int){
        when(effectId){
            Constants.COLOR_EFFECT -> mSeekBarVisibility.value = false
            else -> {
                mPeriod.value = period
                mSeekBarVisibility.value = true
            }
        }
    }

    private fun setEffectAsAvailable(effectId: Int){
        if (mEffectsList[effectId].state == State.DISABLE) {
            mEffectsList[effectId].state = State.AVAILABLE
            mEffectsRecycler.value = effectId
        }
    }

    private fun selectColor(color: Int){
        mColorList.forEach {
            mColor = color
            when(mColor){
                it.color ->  mColorList[mColorList.indexOf(it)].state = State.SELECTED
                else ->  mColorList[mColorList.indexOf(it)].state = State.AVAILABLE
            }
        }
        mColorsRecycler.value = UPDATE_ALL_RECYCLER
    }

    private fun sendDataToDevice(){
        val params = BulbParams (mDevice,
                BulbStatus(true, color = mColor, alpha = mAlpha,
                        effectID = mEffect , period = mPeriod.value?: 0))
        when (mEffect){
            Constants.COLOR_EFFECT -> mSetColorActor.executeSetColor(params)
            else -> {
                mSetColorActor.executeSetEffect(params)
            }
        }

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