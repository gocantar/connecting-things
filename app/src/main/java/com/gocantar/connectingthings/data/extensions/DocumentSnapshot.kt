package com.gocantar.connectingthings.data.extensions

import com.gocantar.connectingthings.data.model.HumidityFB
import com.gocantar.connectingthings.data.model.TemperatureFB
import com.google.firebase.firestore.DocumentSnapshot

/**
 * Created by gocantar on 17/1/18.
 */

fun DocumentSnapshot.toTemperature(): TemperatureFB{
    val t = this
    val temperature = getLong("temperature").toInt()
    val timestamp: Long = getLong("timestamp")
    return TemperatureFB(temperature, timestamp)
}

fun DocumentSnapshot.toHumidity(): HumidityFB{
    val humidity = getLong("humidity").toInt()
    val timestamp: Long = getLong("timestamp")
    return HumidityFB(humidity, timestamp)
}