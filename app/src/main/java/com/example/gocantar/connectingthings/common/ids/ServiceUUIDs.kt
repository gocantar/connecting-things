package com.example.gocantar.connectingthings.common.ids

import java.util.*


/**
 * Created by gocantar on 1/11/17.
 */
class ServiceUUIDs {
    companion object {
        /**
         * General
         */
        val BATTERY = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb")

        /**
         * PlayBulb
         */
        val PLAYBULB_CANDLE_PRIMARY = UUID.fromString("0000ff02-0000-1000-8000-00805f9b34fb")

        val Bulbs: List<UUID> = listOf(PLAYBULB_CANDLE_PRIMARY)


        /**
         * Smart Connect
         */
        val REVOGI_SMART_PLUG_PRIMARY= UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")

        val Plugs: List<UUID> = listOf(REVOGI_SMART_PLUG_PRIMARY)


        /**
         * Sensors
         */
        val ARDUINO1_TEMPERATURE_SENSOR_PRIMARY = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb")

        val Sensors: List<UUID> = listOf(ARDUINO1_TEMPERATURE_SENSOR_PRIMARY)






    }
}