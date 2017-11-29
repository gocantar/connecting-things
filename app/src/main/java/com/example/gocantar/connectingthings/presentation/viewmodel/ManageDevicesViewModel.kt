package com.example.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.gocantar.connectingthings.common.enum.Event
import com.example.gocantar.connectingthings.di.component.AppComponent
import com.example.gocantar.connectingthings.di.component.DaggerManageDevicesComponent
import com.example.gocantar.connectingthings.di.module.ManageDevicesViewModelModule
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.domain.interactor.ConnectDevicesInteractor
import com.example.gocantar.connectingthings.domain.interactor.ScanDevicesInteractor
import com.example.gocantar.connectingthings.presentation.mapper.BLEDeviceViewMapper
import com.example.gocantar.connectingthings.presentation.model.DeviceScannedView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by gocantar on 10/10/17.
 */
class ManageDevicesViewModel(app: Application): BaseViewModel(app) {

    val mDevicesScannedList: MutableMap<String, DeviceScannedView> = mutableMapOf()

    val mRecyclerViewEvent: MutableLiveData<Event> = MutableLiveData()

    @Inject
    lateinit var mScanDevicesActor: ScanDevicesInteractor
    @Inject
    lateinit var mConnectDevicesActor: ConnectDevicesInteractor

    private val mScanDisposable: DisposableObserver<BLEDevice> = object : DisposableObserver<BLEDevice>() {
        override fun onComplete() {
            // Never it's called
        }

        override fun onNext(device: BLEDevice) {
            mDevicesScannedList.put(device.bluetoothDevice.address, BLEDeviceViewMapper.fromBLEDeviceToScannedView(device))
            mRecyclerViewEvent.value = Event.LIST_CHANGED
        }

        override fun onError(e: Throwable?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    fun startScanDevices(){
        mScanDevicesActor.start(mScanDisposable)
    }

    fun stopScanDevices(){
        mScanDevicesActor.stop()
        mScanDisposable.dispose()
    }

    fun connectDevice(deviceScannedView: DeviceScannedView){
        mConnectDevicesActor.connect(deviceScannedView.device, deviceScannedView.typeID, object : DisposableObserver<Event>() {
            override fun onNext(event: Event) {
                when(event){
                    Event.DEVICE_CONNECTED -> Log.d(TAG, "Device has been connected")
                    Event.DEVICE_DISCONNECTED -> Log.d(TAG, "Device has been disconnected")
                    else -> Log.d(TAG, "Device has registered other event")
                }
            }

            override fun onComplete() {
                // Never it's called
            }

            override fun onError(e: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
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