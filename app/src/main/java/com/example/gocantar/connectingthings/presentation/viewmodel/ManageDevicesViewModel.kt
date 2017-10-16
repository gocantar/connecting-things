package com.example.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.example.gocantar.connectingthings.common.base.BaseViewModel
import com.example.gocantar.connectingthings.common.enum.Event
import com.example.gocantar.connectingthings.domain.usecase.ScanDevicesActor
import com.example.gocantar.connectingthings.presentation.mapper.BLEDeviceViewMapper
import com.example.gocantar.connectingthings.presentation.model.BLEDeviceView

/**
 * Created by gocantar on 10/10/17.
 */
class ManageDevicesViewModel(app: Application): BaseViewModel(app) {

    val mDevicesScannedList: MutableMap<String, BLEDeviceView> = mutableMapOf()

    val mRecyclerViewEvent: MutableLiveData<Event> = MutableLiveData()

    private val mScanDevicesActor: ScanDevicesActor = ScanDevicesActor()

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
}