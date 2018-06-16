package com.gocantar.connectingthings.data.model

/**
 * Created by gocantar on 17/1/18.
 */
class HumidityFB( val value: Int,  val timestamp: Long) {

    constructor() : this(-1,-1)

    fun toMap(): Map<String, Any>{
        val map: MutableMap<String, Any> = mutableMapOf()
        map["humidity"] = value
        map["timestamp"] = timestamp
        return map
    }
}