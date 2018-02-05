package com.gocantar.connectingthings.common.ids

import java.util.*

/**
 * Created by gocantar on 4/2/18.
 */
object BLEServicesUUIDs {

    /**
     * Sensors
     */

    val ARDUINO101_WEATHER_STATION_SERVICE = UUID.fromString("0000ffa0-0000-1000-8000-00805f9b34fb")
    val ARDUINO101_TEMPERATURE_CHARACTERISTIC = UUID.fromString("0000ffa1-0000-1000-8000-00805f9b34fb")
    val ARDUINO101_HUMIDITY_CHARACTERISTIC = UUID.fromString("0000ffa2-0000-1000-8000-00805f9b34fb")

    val SENSORS_BLE_SERVICES: Map<UUID, List<UUID>> =
            hashMapOf(
                    ARDUINO101_WEATHER_STATION_SERVICE to listOf(ARDUINO101_TEMPERATURE_CHARACTERISTIC, ARDUINO101_HUMIDITY_CHARACTERISTIC)
            )

}