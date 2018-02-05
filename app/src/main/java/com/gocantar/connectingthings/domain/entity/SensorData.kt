package com.gocantar.connectingthings.domain.entity

import com.gocantar.connectingthings.common.enum.SensorType

/**
 * Created by gocantar on 1/2/18.
 */
data class SensorData(val value: Int, val address: String, val type: SensorType )