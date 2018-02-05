package com.gocantar.connectingthings.domain.entity


/**
 * Created by gocantar on 20/1/18.
 */
data class TemperatureParams (val value: Int, val address: String, val timestamp: Long = System.currentTimeMillis())