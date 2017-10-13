package com.example.gocantar.connectingthings.data

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.gocantar.connectingthings.AppController
import com.example.gocantar.connectingthings.data.mapper.BLEDeviceDataMapper
import com.example.gocantar.connectingthings.domain.boundary.BLEBoundary
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import io.reactivex.subjects.PublishSubject

/**
 * Created by gocantar on 10/10/17.
 */
object BLEService : ScanCallback(), BLEBoundary {

    /**
     * Public properties
     */

    val REQUEST_ENABLE_BT: Int = 4200

    val mPublisher: PublishSubject<BLEDevice> = PublishSubject.create()


    /**
     * Privates properties
     */

    private val TAG: String = javaClass.simpleName

    private val mBluetoothManager: BluetoothManager by lazy { AppController.instance.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager }

    private val mBluetoothAdapter: BluetoothAdapter by lazy { mBluetoothManager.adapter }

    private val mBluetoothLeScanner by lazy { mBluetoothAdapter.bluetoothLeScanner }


    /**
     * Override functions
     */

    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        result?.device?.let {
            mPublisher.onNext(BLEDeviceDataMapper.fromScanResult(result))
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

    /**
     * Object functions
     */

    fun isBLEnabled(): Boolean{
        return mBluetoothAdapter.isEnabled
    }

    fun getRequestBLEIntent(): Intent{
        return Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    }


}