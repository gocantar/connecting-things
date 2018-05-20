package com.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorControllerBoundary
import javax.inject.Inject

/**
 * Created by gocantar on 20/5/18.
 */

class RequestDescriptorValue @Inject constructor(private val mSensorController: TemperatureSensorControllerBoundary){
    fun execute(gatt: BluetoothGatt){
        mSensorController.requestNotificationsState(gatt)
    }
}
