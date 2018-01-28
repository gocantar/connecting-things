package com.gocantar.connectingthings.presentation.model

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.common.ids.TypeID

/**
 * Created by gocantar on 13/10/17.
 */
data class DeviceScannedView(val device: BluetoothDevice, val name: String, val address: String,
                             val numberOfServices: Int, val typeID: TypeID,
                             val bluetoothGatt: BluetoothGatt?, val rssi: Int)