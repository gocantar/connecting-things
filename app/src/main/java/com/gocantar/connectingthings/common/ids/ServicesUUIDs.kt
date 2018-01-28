package com.gocantar.connectingthings.common.ids

import java.util.*


/**
 * Created by gocantar on 1/11/17.
 */
class ServicesUUIDs {
    companion object {
        /**
         * General
         */
        val BATTERY = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb")

        /**
         * PlayBulb
         */
        val PLAYBULB_CANDLE_PRIMARY_SERVICE = UUID.fromString("0000ff02-0000-1000-8000-00805f9b34fb")


        val Bulbs: List<UUID> = listOf(PLAYBULB_CANDLE_PRIMARY_SERVICE)


        /**
         * Smart Connect
         */
        val REVOGI_SMART_PLUG_PRIMARY_SERVICE = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")

        val Plugs: List<UUID> = listOf(REVOGI_SMART_PLUG_PRIMARY_SERVICE)


        /**
         * Sensors
         */
        val ARDUINO101_WEATHER_STATION_SERVICE = UUID.fromString("0000ffa0-0000-1000-8000-00805f9b34fb")

        val Sensors: List<UUID> = listOf(ARDUINO101_WEATHER_STATION_SERVICE)

    }
}