package com.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorControllerBoundary
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.entity.SensorData
import com.gocantar.connectingthings.domain.interactor.DecodeCharacteristicDataInteractor
import javax.inject.Inject

/**
 * Created by gocantar on 1/2/18.
 */
class DecodeSensorDataActor @Inject constructor(private val mSensorController: TemperatureSensorControllerBoundary):
        DecodeCharacteristicDataInteractor<SensorData?>{
    override fun decode(gatt: BluetoothGatt?, charData: CharacteristicData): SensorData?{
        return mSensorController.decode(charData)
    }
}