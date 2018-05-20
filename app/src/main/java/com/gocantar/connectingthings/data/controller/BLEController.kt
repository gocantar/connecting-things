package com.gocantar.connectingthings.controller

import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.os.ParcelUuid
import android.util.Log
import com.gocantar.connectingthings.AppController
import com.gocantar.connectingthings.common.enum.Event
import com.gocantar.connectingthings.common.ids.CharacteristicUUIDs
import com.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.gocantar.connectingthings.common.ids.TypeID
import com.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.gocantar.connectingthings.domain.entity.BLEDevice
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.entity.DeviceEvent
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
    }

    private val mConnectedDevices: MutableMap<String, BLEDevice> = mutableMapOf()

    /**
     * Public properties
     */
    override val mPublisherOfBLEDevice: PublishSubject<BLEDevice> = PublishSubject.create()

    override val mPublisherOfEvent: PublishSubject<DeviceEvent> = PublishSubject.create()

    override val mPublisherOfCharacteristicRead: PublishSubject<CharacteristicData> = PublishSubject.create()

    override val mPublisherOfCharacteristicNotified: PublishSubject<CharacteristicData> = PublishSubject.create()

    override val mPublisherDescriptor: PublishSubject<ByteArray> = PublishSubject.create()


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

    override fun disconnect(address: String) {
        val gatt = mConnectedDevices[address]?.gattBluetoothGatt
        gatt?.let {
            disableNotifications(it)
            it.disconnect()
        }
    }

    override fun enableBLE()  {
        if (!mBluetoothAdapter.isEnabled) {
            mBluetoothAdapter.enable()
        }
    }

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
                            mPublisherOfEvent.onNext(DeviceEvent(gatt.device.address, Event.DEVICE_CONNECTED))
                            // Discovering services
                            gatt.discoverServices()
                        }
                        BluetoothProfile.STATE_DISCONNECTED -> {
                            Log.d(TAG, "${gatt.device.address} has been disconnected to  Gatt client.")
                            mPublisherOfEvent.onNext(DeviceEvent(gatt.device.address, Event.DEVICE_DISCONNECTED))
                            mConnectedDevices.remove(gatt.device.address)
                            Log.d(TAG, mConnectedDevices.toString())
                        }
                    }
                }


                override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                    Log.d(TAG, "Services has been discovered")
                    mConnectedDevices.put(gatt.device.address, BLEDevice(gatt.device, gatt.device.name, gatt.services.map { ParcelUuid(it.uuid) }, gatt))
                    Log.d(TAG, mConnectedDevices.toString())
                }

                override fun onCharacteristicRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {
                    mPublisherOfCharacteristicRead.onNext(CharacteristicData(gatt.device.address,characteristic.uuid, characteristic.value))
                }

                override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
                    mPublisherOfCharacteristicNotified.onNext(CharacteristicData(gatt.device.address, characteristic.uuid, characteristic.value))
                }

                override fun onDescriptorWrite(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor, status: Int) {
                    mPublisherDescriptor.onNext(descriptor.value)
                }

                override fun onDescriptorRead(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor, status: Int) {
                    mPublisherDescriptor.onNext(descriptor.value)
                }


            }

    private fun disableNotifications(gatt: BluetoothGatt){
        val characteristic = getAllCharacteristics(gatt)
        characteristic.forEach {
            gatt.setCharacteristicNotification(it, false)
            writeConfigCharacteristicDescriptor(gatt, it, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)
        }
    }

    private fun getAllCharacteristics(gatt: BluetoothGatt): List<BluetoothGattCharacteristic>{
        return  gatt.getService(ServicesUUIDs.ARDUINO101_WEATHER_STATION_SERVICE)
                .characteristics
    }

    private fun writeConfigCharacteristicDescriptor(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic,
                                                    value: ByteArray?){
        val descriptor = characteristic.getDescriptor(CharacteristicUUIDs.DESCTRIPTOR_CONFIG_CHARACTERISTIC)
        descriptor.value = value
        gatt.writeDescriptor(descriptor)
    }

}




