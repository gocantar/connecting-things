package com.example.gocantar.connectingthings.presentation.model

/**
 * Created by gocantar on 13/10/17.
 */
data class BLEDeviceView(val name: String = "N/A", val mac_address: String, val rssi: Int = 0, val numberOfServices: Int ) {

    override fun equals(other: Any?): Boolean {
        val o = other as BLEDeviceView
        return o.mac_address == mac_address
    }

}