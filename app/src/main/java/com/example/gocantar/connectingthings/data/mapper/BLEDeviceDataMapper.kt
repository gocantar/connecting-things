package com.example.gocantar.connectingthings.data.mapper

import android.bluetooth.le.ScanResult
import com.example.gocantar.connectingthings.domain.entity.BLEDevice

/**
 * Created by gocantar on 13/10/17.
 */
class BLEDeviceDataMapper {

    companion object {
        fun fromScanResult(rslt: ScanResult): BLEDevice{
            return BLEDevice(rslt.device, rslt.rssi, rslt.scanRecord.deviceName ?: "N/A", rslt.scanRecord.serviceUuids ?: listOf() )
        }
    }

}