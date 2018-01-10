package com.example.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import com.example.gocantar.connectingthings.domain.boundary.PlugControllerBoundary
import com.example.gocantar.connectingthings.domain.interactor.SetPlugStatusInteractor
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by gocantar on 10/1/18.
 */
class SetPlugStatusActor @Inject constructor(private val mPlugController: PlugControllerBoundary): SetPlugStatusInteractor {
    override fun turnOff(gatt: BluetoothGatt) {
        doAsync { mPlugController.turnOn(gatt) }
    }

    override fun turnOn(gatt: BluetoothGatt) {
        doAsync { mPlugController.turnOff(gatt) }
    }
}