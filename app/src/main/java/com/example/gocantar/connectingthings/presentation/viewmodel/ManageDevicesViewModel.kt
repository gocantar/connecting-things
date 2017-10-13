package com.example.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import com.example.gocantar.connectingthings.base.BaseViewModel
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.domain.usecase.ScanDevicesActor

/**
 * Created by gocantar on 10/10/17.
 */
class ManageDevicesViewModel(app: Application): BaseViewModel(app) {

    val mDevicesScannedList: MutableMap<String, BLEDevice> = mutableMapOf()

    private val mScanDevicesActor: ScanDevicesActor = ScanDevicesActor()

    fun startScanDevices(){
        mScanDevicesActor.execute {
            mDevicesScannedList.put(it.bluetoothDevice.address, it)
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