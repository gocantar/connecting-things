package com.gocantar.connectingthings.data.extensions

import com.gocantar.connectingthings.data.model.HumidityFB
import com.gocantar.connectingthings.data.model.TemperatureFB
import com.google.firebase.database.DataSnapshot

/**
 * Created by gocantar on 17/1/18.
 */

fun DataSnapshot.toTemperature(): List<TemperatureFB> {
    return this.children.filterNotNull().map {
        val value = it.child("value").getValue(Int::class.java) ?: 0
        val timestamp = it.child("timestamp").getValue(Long::class.java) ?: 0
        TemperatureFB(value, timestamp)
    }
}

fun DataSnapshot.toHumidity(): List<HumidityFB>{
    return  this.children.filterNotNull().map {
        val value = it.child("value").getValue(Int::class.java) ?: 0
        val timestamp = it.child("timestamp").getValue(Long::class.java) ?: 0
        HumidityFB( value, timestamp)
    }
}