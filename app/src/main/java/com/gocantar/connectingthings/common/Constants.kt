package com.gocantar.connectingthings.common

/**
 * Created by gocantar on 20/11/17.
 */
class Constants {
    companion object {

        const val GATT_BLUETOOTH = "gatt_bluetooth"
        const val CHARACTERISTIC_DATA = "characteristic_data"
        const val ADDRESS = "address"
        const val TIMESTAMP = "timestamp"

        const val COLOR_EFFECT =  0
        const val CANDLE_EFFECT = 1
        const val FADE_EFFECT =  2
        const val PULSE_EFFECT = 3
        const val DECREASE_EFFECT =  4
        const val RAINBOW_EFFECT = 5

        const val MIN_CONSUMPTION_LOW = 0
        const val MAX_CONSUMPTION_LOW = 299
        const val MIN_CONSUMPTION_MEDIUM = 300
        const val MAX_CONSUMPTION_MEDIUM = 869
        const val MIN_CONSUMPTION_UPPER_MEDIUM = 870
        const val MAX_CONSUMPTION_UPPER_MEDIUM = 1999
        const val MIN_CONSUMPTION_HIGH = 2000
        const val MAX_CONSUMPTION_HIGH = 4000


    }
}