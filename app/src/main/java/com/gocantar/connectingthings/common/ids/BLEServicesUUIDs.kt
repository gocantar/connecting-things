package com.gocantar.connectingthings.common.ids

import java.util.*

/**
 * Created by gocantar on 4/2/18.
 */
object BLEServicesUUIDs {

    /**
     * Sensors
     */

    val SENSORS_BLE_SERVICES: Map<UUID, List<UUID>> =
            hashMapOf(
                    ServicesUUIDs.ARDUINO101_WEATHER_STATION_SERVICE to listOf(
                            CharacteristicUUIDs.ARDUINO101_TEMPERATURE,
                            CharacteristicUUIDs.ARDUINO101_HUMIDITY)
            )

}