package com.example.gocantar.connectingthings.common.base.extension

import android.os.ParcelUuid
import com.example.gocantar.connectingthings.common.ids.ServiceUUIDs
import com.example.gocantar.connectingthings.common.ids.TypeID

/**
 * Created by gocantar on 1/11/17.
 */

fun List<ParcelUuid>.getType(): TypeID {
    return when {
        find { ServiceUUIDs.Bulbs.contains(it.uuid) } != null -> TypeID.BULB
        find { ServiceUUIDs.Sensors.contains(it.uuid) } != null -> TypeID.SENSOR
        else -> TypeID.NO_TYPE
    }
}

