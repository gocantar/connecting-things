package com.gocantar.connectingthings.data.model

/**
 * Created by gocantar on 17/1/18.
 */
class HumidityFB( val humidity: Int,  val timestamp: Long) {

    constructor() : this(-1,-1)

    fun toMap(): Map<String, Any>{
        val map: MutableMap<String, Any> = mutableMapOf()
        map.put("humidity", humidity)
        map.put("timestamp", timestamp)
        return map
    }
}