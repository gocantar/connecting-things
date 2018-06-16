package com.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorControllerBoundary
import com.gocantar.connectingthings.domain.interactor.ManageNotificationsInteractor
import javax.inject.Inject

/**
 * Created by gocantar on 30/1/18.
 */
class ManageSensorNotificationsActor @Inject constructor(private val mSensorController: TemperatureSensorControllerBoundary):
            ManageNotificationsInteractor {

    override fun enable(gatt: BluetoothGatt) {
        mSensorController.enableNotifications(gatt)
    }

    override fun disable(gatt: BluetoothGatt) {
        mSensorController.disableNotifications(gatt)
    }

}

