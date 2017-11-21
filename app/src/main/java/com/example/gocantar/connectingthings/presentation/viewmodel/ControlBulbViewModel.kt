package com.example.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.common.enum.State
import com.example.gocantar.connectingthings.di.component.AppComponent
import com.example.gocantar.connectingthings.di.component.DaggerBulbControllerComponent
import com.example.gocantar.connectingthings.di.module.BulbControllerModule
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.domain.entity.BulbParams
import com.example.gocantar.connectingthings.domain.usecase.GetDeviceActor
import com.example.gocantar.connectingthings.domain.usecase.SetColorActor
import com.example.gocantar.connectingthings.presentation.model.BulbColor
import com.example.gocantar.connectingthings.presentation.model.BulbEffect
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by gocantar on 14/11/17.
 */

class ControlBulbViewModel(app: Application): BaseViewModel(app){

    val mEffectsList: List<BulbEffect> by lazy {
        mResources.getStringArray(R.array.effects).asList()
                .map { BulbEffect(it, State.DISABLE) }
    }

    val mColorList: List<BulbColor> by lazy {
        mResources.getIntArray(R.array.bulb_colors_palette).asList()
                .map { BulbColor(it, State.DISABLE) }
    }

    var mColor: Int = mResources.getColor(R.color.white, app.theme)

    var mAlpha: Int = 0

    var mEffect: String = mEffectsList.first().effect

    var mDevice: BLEDevice? = null

    @Inject lateinit var mGetDeviceActor: GetDeviceActor
    @Inject lateinit var mSetColorActor: SetColorActor

    /**
     * Data binding variables
     */

    val ba_title: MutableLiveData<String> = MutableLiveData()


    /**
     * ---------------------------------------------------------
     */

    fun initialize(address: String){

        mEffect = mEffectsList.first().effect

        mGetDeviceActor.execute(object : DisposableObserver<BLEDevice>() {
            override fun onError(e: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onNext(device: BLEDevice?) {
                mDevice = device
                ba_title.value = device?.name ?: mResources.getString(R.string.error)
            }

            override fun onComplete() {
                Log.d(TAG, "Device connected was gotten")
            }

        }, address )
    }

    fun putColor(){
        mDevice?.let {
            mSetColorActor.execute(BulbParams(it, mColor, mAlpha, mEffect))
        }
    }

    fun onMessageReceived(characterisctic: String, message: ByteArray){}

    override fun onCleared() {
        super.onCleared()
        mGetDeviceActor.dispose()
    }

    override fun setUpComponent(appComponent: AppComponent) {
        DaggerBulbControllerComponent.builder()
                .appComponent(appComponent)
                .bulbControllerModule(BulbControllerModule(this))
                .build()
                .inject(this)
    }
}