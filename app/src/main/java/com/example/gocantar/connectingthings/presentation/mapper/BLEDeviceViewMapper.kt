package com.example.gocantar.connectingthings.presentation.mapper

import com.example.gocantar.connectingthings.common.base.extension.getType
import com.example.gocantar.connectingthings.common.ids.TypeID
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.presentation.model.BLEDeviceView

/**
 * Created by gocantar on 13/10/17.
 */
class BLEDeviceViewMapper {

    companion object {
        fun fromBLEDevice(device: BLEDevice ): BLEDeviceView {
            return BLEDeviceView(device.bluetoothDevice, device.name, device.bluetoothDevice.address,  device.uuids.size, device.uuids.getType(), device.gattBluetoothGatt ,device.rssi)
        }
    }

}