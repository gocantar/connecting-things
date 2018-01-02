package com.example.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothDevice
import com.example.gocantar.connectingthings.common.ids.TypeID
import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.domain.interactor.ConnectDevicesInteractor
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by gocantar on 3/11/17.
 */
class ConnectDevicesActor @Inject constructor(private val mBLEService: BLEServiceBoundary): ConnectDevicesInteractor {

    override fun connect(device: BluetoothDevice, type: TypeID) {
        doAsync { mBLEService.connect(device, type) }
    }

    override fun disconnect(address: String) {
        doAsync { mBLEService.disconnect(address) }
    }

}