package com.example.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.bluetooth.BluetoothDevice
import android.util.Log
import com.example.gocantar.connectingthings.common.enum.Event
import com.example.gocantar.connectingthings.di.component.AppComponent
import com.example.gocantar.connectingthings.di.component.DaggerManageDevicesComponent
import com.example.gocantar.connectingthings.di.module.ManageDevicesViewModelModule
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.domain.entity.DeviceEvent
import com.example.gocantar.connectingthings.domain.interactor.ConnectDevicesInteractor
import com.example.gocantar.connectingthings.domain.interactor.ScanDevicesInteractor
import com.example.gocantar.connectingthings.domain.usecase.GetConnectedDevicesActor
import com.example.gocantar.connectingthings.presentation.mapper.BLEDeviceViewMapper
import com.example.gocantar.connectingthings.presentation.model.DeviceScannedView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by gocantar on 10/10/17.
 */
class ManageDevicesViewModel(app: Application): BaseViewModel(app) {

    val mDevicesScannedList: MutableMap<String, DeviceScannedView> = mutableMapOf()
    val mDevicesConnectedList: MutableMap<String, DeviceScannedView> = mutableMapOf()
    val mSubscriptions: MutableList<String> = mutableListOf()

    val mRecyclerViewEvent: MutableLiveData<Event> = MutableLiveData()

    @Inject lateinit var mScanDevicesActor: ScanDevicesInteractor
    @Inject lateinit var mConnectDevicesActor: ConnectDevicesInteractor
    @Inject lateinit var mGetConnectedDevicesActor: GetConnectedDevicesActor

    private val mScanDisposable: DisposableObserver<BLEDevice> = object : DisposableObserver<BLEDevice>() {
        override fun onComplete() {
            // Never it's called
        }

        override fun onNext(device: BLEDevice) {
            addScannedDevice(device)
        }

        override fun onError(e: Throwable?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    fun initialize(){
        mDevicesConnectedList.clear()
        mGetConnectedDevicesActor.execute(object: DisposableObserver<BLEDevice>(){
            override fun onNext(device: BLEDevice) {
                mDevicesConnectedList.put(device.bluetoothDevice.address, BLEDeviceViewMapper.fromBLEDeviceToScannedView(device))
            }

            override fun onError(e: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onComplete() {
                Log.d(TAG, "The connected devices has been gotten correctly")
            }

        }, Unit)
    }

    fun startScanDevices(){
        mScanDevicesActor.start(mScanDisposable)
    }

    fun stopScanDevices(){
        mScanDevicesActor.stop()
        mScanDisposable.dispose()
    }

    fun connectDevice(deviceScannedView: DeviceScannedView){
        val subscribe = !mSubscriptions.contains(deviceScannedView.address)
        if (subscribe) {
            mSubscriptions.add(deviceScannedView.address)
        }
        mConnectDevicesActor.connect(deviceScannedView.device, deviceScannedView.typeID, object : DisposableObserver<DeviceEvent>() {
            override fun onNext(deviceEvent: DeviceEvent) {
                when(deviceEvent.event){
                    Event.DEVICE_CONNECTED -> {
                        Log.d(TAG, "Devices ${deviceEvent.address} added to list of connected devices")
                        addConnectedDevices(deviceEvent.address)
                    }
                    Event.DEVICE_DISCONNECTED -> {
                        Log.d(TAG, "Device has been disconnected")
                        removeConnectedDevice(deviceEvent.address)
                    }
                    else -> Log.d(TAG, "Other devices event has been registered")
                }
            }
            override fun onComplete() {
                // Never it's called
            }
            override fun onError(e: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }, subscribe)
    }

    fun disconnectDevice(address: String) = mConnectDevicesActor.disconnect(address)

    private fun addScannedDevice(device: BLEDevice){
        mDevicesConnectedList.remove(device.bluetoothDevice.address)
        mDevicesScannedList.put(device.bluetoothDevice.address, BLEDeviceViewMapper.fromBLEDeviceToScannedView(device))
        mRecyclerViewEvent.value = Event.LIST_CHANGED
    }

    private fun addConnectedDevices(address: String){
        val deviceConnected = mDevicesScannedList.remove(address)
        deviceConnected?.let{
            mDevicesConnectedList.put(address, deviceConnected)
        }
        mRecyclerViewEvent.value = Event.LIST_CHANGED
    }

    private fun removeConnectedDevice(address: String){
        mDevicesConnectedList.remove(address)
    }

    override fun onCleared() {
        super.onCleared()
        mConnectDevicesActor.dispose()
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