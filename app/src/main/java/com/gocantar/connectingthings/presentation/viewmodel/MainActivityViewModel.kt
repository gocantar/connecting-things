package com.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.gocantar.connectingthings.common.enum.Event
import com.gocantar.connectingthings.common.ids.TypeID
import com.gocantar.connectingthings.di.component.AppComponent
import com.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.gocantar.connectingthings.domain.entity.BLEDevice
import com.gocantar.connectingthings.domain.usecase.GetConnectedDevicesActor
import com.gocantar.connectingthings.common.extension.getType
import com.gocantar.connectingthings.domain.entity.DeviceEvent
import com.gocantar.connectingthings.domain.usecase.GetBLENotificationsActor
import com.gocantar.connectingthings.presentation.mapper.BLEDeviceViewMapper
import com.gocantar.connectingthings.presentation.model.BulbConnectedView
import com.gocantar.connectingthings.presentation.model.DeviceScannedView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by gocantar on 17/10/17.
 */
class MainActivityViewModel(app: Application): BaseViewModel(app) {

    val mRecyclerViewEvent: MutableLiveData<Event> = MutableLiveData()

    val mBulbsConnected: MutableList<BulbConnectedView> = mutableListOf()
    val mSensorsConnected: MutableList<DeviceScannedView> = mutableListOf()
    val mPlugsConnected: MutableList<DeviceScannedView> = mutableListOf()

    @Inject lateinit var mBLEServiceService: BLEServiceBoundary
    @Inject lateinit var mGetConnectedDevicesActor: GetConnectedDevicesActor
    @Inject lateinit var mBLENotificationsActor: GetBLENotificationsActor

    /**
     * Public fun
     */
    fun enableBLE() = mBLEServiceService.enableBLE()

    fun initialize(){
        mBLENotificationsActor.execute(object : DisposableObserver<DeviceEvent>() {
            override fun onComplete() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onNext(event: DeviceEvent?) {
                event?.let {
                    if(it.event == Event.DEVICE_DISCONNECTED){
                        when{
                            mBulbsConnected.removeIf { it.address == event.address } -> mRecyclerViewEvent.value = Event.BULB_LIST_CHANGED
                            mSensorsConnected.removeIf { it.address == event.address } ->  mRecyclerViewEvent.value = Event.SENSOR_LIST_CHANGED
                            mPlugsConnected.removeIf { it.address == event.address } -> mRecyclerViewEvent.value = Event.PLUG_LIST_CHANGED
                        }
                    }
                }
            }

            override fun onError(e: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }, Unit)
    }

    fun updateDevicesConnected(){
        val bulbs = mutableListOf<BulbConnectedView>()
        val plugs = mutableListOf<DeviceScannedView>()
        val sensors = mutableListOf<DeviceScannedView>()
        mGetConnectedDevicesActor.execute(object : DisposableObserver<BLEDevice>() {
            override fun onNext(device: BLEDevice) {
                when(device.uuids.getType()){
                    TypeID.BULB -> bulbs.add(BLEDeviceViewMapper.fromBLEDeviceToBulbConnectedView(device))
                    TypeID.PLUG -> plugs.add(BLEDeviceViewMapper.fromBLEDeviceToScannedView(device))
                    TypeID.SENSOR -> sensors.add(BLEDeviceViewMapper.fromBLEDeviceToScannedView(device))
                    else -> {
                        Log.e(TAG, "Device's typeID is wrong")
                    }
                }
            }
            override fun onComplete() {
                Log.d(TAG, "List of connected devices has been updated")
                if (updateBulbList(bulbs)) {
                    mRecyclerViewEvent.value = Event.BULB_LIST_CHANGED
                }
                if (updatePlugList(plugs)){
                    mRecyclerViewEvent.value = Event.PLUG_LIST_CHANGED
                }
                if (updateSensorList(sensors)){
                    mRecyclerViewEvent.value = Event.SENSOR_LIST_CHANGED
                }
            }
            override fun onError(e: Throwable?) {
                Log.e(TAG, e?.message)
            }
        }, Unit)
    }

    fun getBulbsRecyclerViewVisibility(): Boolean = mBulbsConnected.isNotEmpty()

    fun getPlugsRecyclerViewVisibility(): Boolean = mPlugsConnected.isNotEmpty()

    fun getSensorsRecyclerViewVisibility(): Boolean = mSensorsConnected.isNotEmpty()

    /**
     * Private fun
     */
    private fun updateBulbList(list: MutableList<BulbConnectedView>): Boolean {
        val itemRemoved = mBulbsConnected.retainAll(list)
        list.removeAll(mBulbsConnected)
        val itemAdded = mBulbsConnected.addAll(list)
        return  itemRemoved || itemAdded
    }

    private fun updatePlugList(list: MutableList<DeviceScannedView>): Boolean {
        val itemRemoved = mPlugsConnected.retainAll(list)
        list.removeAll(mPlugsConnected)
        val itemAdded = mPlugsConnected.addAll(list)
        return itemRemoved || itemAdded
    }

    private fun updateSensorList(list: MutableList<DeviceScannedView>): Boolean {
        val itemRemoved = mSensorsConnected.retainAll(list)
        list.removeAll(mSensorsConnected)
        val itemAdded = mSensorsConnected.addAll(list)
        return itemRemoved || itemAdded
    }

    /**
     * Override fun
     */
    override fun onCleared() {
        super.onCleared()
        mGetConnectedDevicesActor.dispose()
    }

    override fun setUpComponent(appComponent: AppComponent) {
        appComponent.inject(this)
    }




}