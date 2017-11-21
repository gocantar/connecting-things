package com.example.gocantar.connectingthings.data.controller

import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.os.ParcelUuid
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.example.gocantar.connectingthings.AppController
import com.example.gocantar.connectingthings.common.Constants
import com.example.gocantar.connectingthings.common.enum.Event
import com.example.gocantar.connectingthings.common.ids.TypeID
import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.subjects.PublishSubject

import javax.inject.Inject

/**
 * Created by gocantar on 10/10/17.
 */

class BLEController @Inject constructor(private val mBluetoothManager: BluetoothManager) : ScanCallback(), BLEServiceBoundary {

    /**
     *  Static properties
     */
    companion object {

        val REQUEST_CODE: Int = 4200

        private val mConnectedDevices: MutableMap<String, BLEDevice> = mutableMapOf()

    }

    /**
     * Public properties
     */
    override val mPublisherOfBLEDevice: PublishSubject<BLEDevice> = PublishSubject.create()

    override val mPublisherOfEvent: PublishSubject<Event> = PublishSubject.create()


    /**
     * Privates properties
     */
    private val TAG: String = javaClass.simpleName

    private val mBluetoothAdapter: BluetoothAdapter by lazy { mBluetoothManager.adapter }

    private val mBluetoothLeScanner by lazy { mBluetoothAdapter.bluetoothLeScanner }



    /**
     * Override functions
     */
    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        result?.device?.let {
            mPublisherOfBLEDevice.onNext(BLEDevice(result.device,result.scanRecord.deviceName ?: "N/A",
                    result.scanRecord.serviceUuids ?: listOf(), null ,result.rssi))
        }
    }

    override fun onScanFailed(errorCode: Int) {
        Log.d(TAG, "onScanFailed: error code $errorCode")
    }

    override fun start() {
        mBluetoothLeScanner.startScan(this)
    }

    override fun stop() {
        mBluetoothLeScanner.stopScan(this)
    }

    override fun connect(bluetoothDevice: BluetoothDevice, typeID: TypeID) {
        bluetoothDevice.connectGatt(AppController.instance, false, getGattCallback(), BluetoothDevice.TRANSPORT_LE )
    }

    override fun disconnect(bluetoothDevice: BluetoothDevice) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isBLEnabled(): Boolean = mBluetoothAdapter.isEnabled

    override fun getConnectedDevices(): Observable<BLEDevice> =
            mBluetoothManager.getConnectedDevices(BluetoothProfile.GATT)
                    .mapNotNull { mConnectedDevices[it.address] }
                    .toObservable()

    override fun getDevice(address: String): Observable<BLEDevice> {
        return mBluetoothManager.getConnectedDevices(BluetoothProfile.GATT)
                .mapNotNull { mConnectedDevices[it.address] }
                .toObservable()
                .filter { it.bluetoothDevice.address == address }
                .distinctUntilChanged()
    }


    override fun getRequestBLEIntent(): Intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)


    /**
     * Private functions
     */
    private fun getGattCallback(): BluetoothGattCallback =
            object : BluetoothGattCallback() {
                override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                    when (newState){
                        BluetoothProfile.STATE_CONNECTED -> {
                            Log.d(TAG, "${gatt.device.address} has been connected to  Gatt client. Attempting to start service discovery")
                            mPublisherOfEvent.onNext(Event.DEVICE_CONNECTED)
                            // Discovering services
                            gatt.discoverServices()
                        }
                        BluetoothProfile.STATE_DISCONNECTED -> {
                            Log.d(TAG, "${gatt.device.address} has been disconnected to  Gatt client.")
                            mPublisherOfEvent.onNext(Event.DEVICE_DISCONNECTED)
                            mConnectedDevices.remove(gatt.device.address)
                        }
                    }
                }

                override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {

                    Log.d(TAG, "Services were discovered")
                    gatt.services?.forEach { Log.d(TAG, it?.uuid.toString()) }

                    mConnectedDevices.put(gatt.device.address, BLEDevice(gatt.device, gatt.device.name, gatt.services.map { ParcelUuid(it.uuid) }, gatt))


                }

                override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
                    characteristic?.let {
                        val intent = Intent(Constants.DEVICE_DATA_ACTION)
                        intent.putExtra(Constants.DATA_RECEIVED, characteristic.value)
                        intent.putExtra(Constants.CHARACTERISTIC, characteristic.uuid.toString())
                        LocalBroadcastManager.getInstance(AppController.instance.baseContext)
                    }
                }
            }

}




