package com.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.common.enum.State
import com.gocantar.connectingthings.domain.boundary.PlugControllerBoundary
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.interactor.DecodeCharacteristicDataInteractor
import javax.inject.Inject

/**
 * Created by gocantar on 11/1/18.
 */
class DecodeLivePowerConsumptionActor @Inject constructor(private val mPlugController: PlugControllerBoundary):
        DecodeCharacteristicDataInteractor<Pair<Int, State>> {
    override fun decode(gatt: BluetoothGatt?, charData: CharacteristicData): Pair<Int, State> {
        gatt?.let {
            return mPlugController.decodePowerConsumption(gatt, charData)
        }
        return Pair(0, State.DISABLE)
    }
}