package com.example.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import com.example.gocantar.connectingthings.domain.boundary.BulbControllerBoundary
import com.example.gocantar.connectingthings.domain.interactor.ReadBulbCharacteristicInteractor
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by gocantar on 5/1/18.
 */
class ReadBulbCharacteristicActor @Inject constructor(private val mBulbController: BulbControllerBoundary):
        ReadBulbCharacteristicInteractor {
    override fun execute(gatt: BluetoothGatt) {
        doAsync {
            mBulbController.readCharacteristic(gatt)
        }
    }
}