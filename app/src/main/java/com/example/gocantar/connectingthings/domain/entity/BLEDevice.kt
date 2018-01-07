package com.example.gocantar.connectingthings.domain.entity

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.os.ParcelUuid

/**
 * Created by gocantar on 13/10/17.
 */
data class BLEDevice (val bluetoothDevice: BluetoothDevice, val name: String ,
                      val uuids: List<ParcelUuid>, val gattBluetoothGatt: BluetoothGatt? ,
                      val rssi: Int = 0 )
