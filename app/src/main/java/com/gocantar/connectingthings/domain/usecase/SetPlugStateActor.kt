package com.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.domain.boundary.PlugControllerBoundary
import com.gocantar.connectingthings.domain.interactor.SetPlugStateInteractor
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by gocantar on 10/1/18.
 */
class SetPlugStateActor @Inject constructor(private val mPlugController: PlugControllerBoundary): SetPlugStateInteractor {
    override fun turnOff(gatt: BluetoothGatt) {
        doAsync { mPlugController.turnOff(gatt) }
    }

    override fun turnOn(gatt: BluetoothGatt) {
        doAsync { mPlugController.turnOn(gatt) }
    }
}