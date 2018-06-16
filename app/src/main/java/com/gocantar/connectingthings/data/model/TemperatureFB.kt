package com.gocantar.connectingthings.data.model

/**
 * Created by gocantar on 17/1/18.
 */
data class TemperatureFB(val value: Int, val timestamp: Long) {

    constructor() : this(-1,-1)

    fun toMap(): Map<String, Any>{
        val map: MutableMap<String, Any> = mutableMapOf()
        map["temperature"] = value
        map["timestamp"] = timestamp
        return map
    }
}





