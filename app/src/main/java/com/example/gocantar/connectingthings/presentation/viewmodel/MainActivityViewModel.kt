package com.example.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.util.Log
import com.example.gocantar.connectingthings.common.base.BaseViewModel
import com.example.gocantar.connectingthings.common.ids.TypeID
import com.example.gocantar.connectingthings.di.component.AppComponent
import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.domain.usecase.GetConnectedDevicesActor
import com.example.gocantar.connectingthings.presentation.mapper.BLEDeviceViewMapper
import com.example.gocantar.connectingthings.presentation.model.BLEDeviceView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by gocantar on 17/10/17.
 */
class MainActivityViewModel(app: Application): BaseViewModel(app) {

    private val mBulbsConnected: MutableMap<String, BLEDeviceView> = mutableMapOf()
    private val mSensorsConnected: MutableMap<String, BLEDeviceView> = mutableMapOf()
    private val mPlugsConnected: MutableMap<String, BLEDeviceView> = mutableMapOf()

    @Inject
    lateinit var mBLEServiceService: BLEServiceBoundary
    @Inject
    lateinit var mGetConnectedDevicesActor: GetConnectedDevicesActor


    fun isBLEEnabled(): Boolean = mBLEServiceService.isBLEnabled()

    fun getRequestBLEIntent() = mBLEServiceService.getRequestBLEIntent()

    fun updateDevicesConnected(){
        clearMaps()
        mGetConnectedDevicesActor.execute(object : DisposableObserver<BLEDevice>() {
            override fun onNext(device: BLEDevice) {
                val deviceView = BLEDeviceViewMapper.fromBLEDevice(device)
                when(deviceView.typeID){
                    TypeID.BULB -> mBulbsConnected.put(deviceView.mac_address, deviceView)
                    TypeID.PLUG -> mPlugsConnected.put(deviceView.mac_address, deviceView)
                    TypeID.SENSOR -> mSensorsConnected.put(deviceView.mac_address, deviceView)
                    else -> {
                        Log.e(TAG, "Device's typeID is wrong")
                    }
                }
            }
            override fun onComplete() {
                // notified list is completed
                Log.d(TAG, "List of connected devices has been updated")
            }
            override fun onError(e: Throwable?) {
                Log.e(TAG, e?.message)
            }
        })
    }

    private fun clearMaps(){
        mBulbsConnected.clear()
        mPlugsConnected.clear()
        mSensorsConnected.clear()
    }

    override fun onCleared() {
        super.onCleared()
        mGetConnectedDevicesActor.dispose()
    }

    override fun setUpComponent(appComponent: AppComponent) {
        appComponent.inject(this)
    }



}