package com.example.gocantar.connectingthings.common

/**
 * Created by gocantar on 20/11/17.
 */
class Constants {
    companion object {

        val GATT_BLUETOOTH = "gatt_bluetooth"
        val CHARACTERISTIC_DATA = "characteristic_data"

        val COLOR_EFFECT =  0
        val CANDLE_EFFECT = 1
        val FADE_EFFECT =  2
        val PULSE_EFFECT = 3
        val DECREASE_EFFECT =  4
        val RAINBOW_EFFECT = 5

        val MIN_CONSUMPTION_LOW = 0
        val MAX_CONSUMPTION_LOW = 299
        val MIN_CONSUMPTION_MEDIUM = 300
        val MAX_CONSUMPTION_MEDIUM = 869
        val MIN_CONSUMPTION_UPPER_MEDIUM = 870
        val MAX_CONSUMPTION_UPPER_MEDIUM = 1999
        val MIN_CONSUMPTION_HIGH = 2000
        val MAX_CONSUMPTION_HIGH = 4000


    }
}