package com.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.domain.boundary.PlugControllerBoundary
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.interactor.DecodeLivePowerConsumptionInteractor
import javax.inject.Inject

/**
 * Created by gocantar on 11/1/18.
 */
class DecodeLivePowerConsumptionActor @Inject constructor(private val mPlugController: PlugControllerBoundary):
        DecodeLivePowerConsumptionInteractor{
    override fun decode(gatt: BluetoothGatt, charData: CharacteristicData): Int {
        return mPlugController.decodePowerConsumption(gatt, charData)
    }
}