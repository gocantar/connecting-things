package com.example.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import com.example.gocantar.connectingthings.domain.boundary.PlugControllerBoundary
import com.example.gocantar.connectingthings.domain.interactor.ManageNotificationsInteractor
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by gocantar on 10/1/18.
 */


class ManagePlugNotificationsActor @Inject constructor(private val mPlugController: PlugControllerBoundary): ManageNotificationsInteractor {
    override fun enable(gatt: BluetoothGatt) {
        doAsync { mPlugController.enableNotifications(gatt) }
    }

    override fun disable(gatt: BluetoothGatt) {
        doAsync { mPlugController.disableNotifications(gatt) }
    }
}
