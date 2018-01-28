package com.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothDevice
import com.gocantar.connectingthings.common.ids.TypeID
import com.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.gocantar.connectingthings.domain.interactor.ConnectDevicesInteractor
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