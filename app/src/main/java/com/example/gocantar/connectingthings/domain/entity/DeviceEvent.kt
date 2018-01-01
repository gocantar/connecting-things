package com.example.gocantar.connectingthings.domain.entity

import com.example.gocantar.connectingthings.common.enum.Event

/**
 * Created by gocantar on 31/12/17.
 */
data class DeviceEvent(val address: String, val event: Event) {
}