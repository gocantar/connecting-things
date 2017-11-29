package com.example.gocantar.connectingthings.presentation.mapper

import android.graphics.Color
import com.example.gocantar.connectingthings.AppController
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.presentation.extension.getType
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.presentation.model.BulbConnectedView
import com.example.gocantar.connectingthings.presentation.model.DeviceScannedView

/**
 * Created by gocantar on 13/10/17.
 */
class BLEDeviceViewMapper {

    companion object {
        fun fromBLEDeviceToScannedView(device: BLEDevice ): DeviceScannedView {
            return DeviceScannedView(device.bluetoothDevice, device.name, device.bluetoothDevice.address,  device.uuids.size, device.uuids.getType(), device.gattBluetoothGatt ,device.rssi)
        }

        fun fromBLEDeviceToBulbConnectedView(device: BLEDevice):BulbConnectedView {
            return BulbConnectedView(device.bluetoothDevice, device.name, device.bluetoothDevice.address, device.gattBluetoothGatt )
        }
    }

}