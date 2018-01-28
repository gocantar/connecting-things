package com.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.domain.boundary.BulbControllerBoundary
import com.gocantar.connectingthings.domain.interactor.ReadCharacteristicInteractor
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by gocantar on 5/1/18.
 */
class ReadBulbStateActor @Inject constructor(private val mBulbController: BulbControllerBoundary):
        ReadCharacteristicInteractor {
    override fun execute(gatt: BluetoothGatt) {
        doAsync {
            mBulbController.requestStatus(gatt)
        }
    }
}