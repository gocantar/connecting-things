package com.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorControllerBoundary
import com.gocantar.connectingthings.domain.interactor.DisposableInteractor
import com.gocantar.connectingthings.domain.interactor.ManageNotificationsInteractor
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by gocantar on 30/1/18.
 */
class ManageSensorNotificationsActor @Inject constructor(private val mSensorController: TemperatureSensorControllerBoundary):
            ManageNotificationsInteractor, DisposableInteractor {

    override fun enable(gatt: BluetoothGatt) {
        doAsync { mSensorController.enableNotifications(gatt) }
    }

    override fun disable(gatt: BluetoothGatt) {
        doAsync { mSensorController.disableNotifications(gatt) }
    }

    override fun dispose() {
        mSensorController.clearDisposables()
    }
}

