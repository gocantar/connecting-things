package com.gocantar.connectingthings.domain.entity

import com.gocantar.connectingthings.common.enum.Event

/**
 * Created by gocantar on 31/12/17.
 */
data class DeviceEvent(val address: String, val event: Event)