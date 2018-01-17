package com.gocantar.connectingthings.data.model

import java.sql.Timestamp

/**
 * Created by gocantar on 17/1/18.
 */
data class TemperatureFB(val temperature: Int, val timestamp: Long) {

    constructor() : this(-1,-1)

    fun toMap(): Map<String, Any>{
        val map: MutableMap<String, Any> = mutableMapOf()
        map.put("temperature", temperature)
        map.put("timestamp", timestamp)
        return map
    }
}

