package com.gocantar.connectingthings.domain.boundary

import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.entity.SensorData

/**
 * Created by gocantar on 23/1/18.
 */
interface TemperatureSensorControllerBoundary: ManageNotificationsBoundary, NotificationsState {
    fun clearDisposables()
    fun decode(charData: CharacteristicData): SensorData?
}