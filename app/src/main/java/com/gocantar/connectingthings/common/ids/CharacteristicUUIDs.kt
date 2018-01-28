package com.gocantar.connectingthings.common.ids

import java.util.*

/**
 * Created by gocantar on 1/11/17.
 */
class CharacteristicUUIDs {
    companion object {
        /**
         * Descriptor
         */
        val DESCTRIPTOR_CONFIG_CHARACTERISTIC = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")

        /**
         * Play Bulb
         */
        val PLAYBULB_CANDLE_CHANGE_COLOR = UUID.fromString("0000fffc-0000-1000-8000-00805f9b34fb")
        val PLAYBULB_CANDLE_CHANGE_EFFECT = UUID.fromString("0000fffb-0000-1000-8000-00805f9b34fb")

        /**
         * Smart Connect
         */
        val REVOGI_SMART_PLUG_REQUEST =  UUID.fromString("0000fff3-0000-1000-8000-00805f9b34fb")
        val REVOGI_SMART_PLUG_RESPONSE = UUID.fromString("0000fff4-0000-1000-8000-00805f9b34fb")

        /**
         * Sensor
         */
        val ARDUINO101_TEMPERATURE = UUID.fromString("0000ffa1-0000-1000-8000-00805f9b34fb")
        val ARDUINO101_HUMIDITY = UUID.fromString("0000ffa2-0000-1000-8000-00805f9b34fb")
    }
}