package com.example.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.di.component.AppComponent
import com.example.gocantar.connectingthings.di.component.DaggerPlugControllerComponent
import com.example.gocantar.connectingthings.di.module.PlugControllerModule
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.domain.usecase.GetDeviceActor
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by gocantar on 29/11/17.
 */
class ControlPlugViewModel(app: Application): BaseViewModel(app) {


    lateinit var mDevice: BLEDevice


    @Inject lateinit var mGetDeviceActor: GetDeviceActor

    private var mDisposable: DisposableObserver<BLEDevice> = object : DisposableObserver<BLEDevice>() {
        override fun onComplete() {
            Log.d(TAG, "Device connected was gotten")
        }

        override fun onError(error: Throwable?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onNext(device: BLEDevice?) {
            device?.let {
                mDevice = device
            }
            pa_title.value = device?.name ?: mResources.getString(R.string.error)
        }

    }

    /**
     * Data binding variables
     */

    val pa_title: MutableLiveData<String> = MutableLiveData()

    /**
     * ---------------------------------------------------------
     */

    fun initialize(address: String){
        mGetDeviceActor.execute(mDisposable, address)
    }


    override fun setUpComponent(appComponent: AppComponent) {
        DaggerPlugControllerComponent.builder()
                .appComponent(appComponent)
                .plugControllerModule(PlugControllerModule(this))
                .build()
                .inject(this)
    }
}