package com.gocantar.connectingthings.domain.interactor

import android.bluetooth.BluetoothDevice
import com.gocantar.connectingthings.common.ids.TypeID

/**
 * Created by gocantar on 3/11/17.
 */
interface ConnectDevicesInteractor {
    fun connect(device: BluetoothDevice, type: TypeID)
    fun disconnect(address:String)
}