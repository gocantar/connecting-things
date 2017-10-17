package com.example.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.example.gocantar.connectingthings.common.base.BaseViewModel
import com.example.gocantar.connectingthings.common.enum.Event
import com.example.gocantar.connectingthings.di.component.AppComponent
import com.example.gocantar.connectingthings.di.component.DaggerManageDevicesComponent
import com.example.gocantar.connectingthings.di.module.ManageDevicesViewModelModule
import com.example.gocantar.connectingthings.domain.usecase.ScanDevicesActor
import com.example.gocantar.connectingthings.presentation.mapper.BLEDeviceViewMapper
import com.example.gocantar.connectingthings.presentation.model.BLEDeviceView
import javax.inject.Inject

/**
 * Created by gocantar on 10/10/17.
 */
class ManageDevicesViewModel(app: Application): BaseViewModel(app) {

    val mDevicesScannedList: MutableMap<String, BLEDeviceView> = mutableMapOf()

    val mRecyclerViewEvent: MutableLiveData<Event> = MutableLiveData()

    @Inject
    lateinit var mScanDevicesActor: ScanDevicesActor

    fun startScanDevices(){
        mScanDevicesActor.execute {
            mDevicesScannedList.put(it.bluetoothDevice.address, BLEDeviceViewMapper.fromBLEDevice(it))
            mRecyclerViewEvent.value = Event.LIST_CHANGED
        }
    }

    fun stopScanDevices(){
        mScanDevicesActor.stop()
    }

    override fun onCleared() {
        super.onCleared()
        mScanDevicesActor.dispose()
    }

    override fun setUpComponent(appComponent: AppComponent) {
        DaggerManageDevicesComponent.builder()
                .appComponent(appComponent)
                .manageDevicesViewModelModule(ManageDevicesViewModelModule(this))
                .build()
                .inject(this)
    }
}