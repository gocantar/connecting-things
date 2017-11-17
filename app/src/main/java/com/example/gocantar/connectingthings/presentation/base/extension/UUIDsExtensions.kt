package com.example.gocantar.connectingthings.presentation.base.extension

import android.os.ParcelUuid
import com.example.gocantar.connectingthings.common.ids.UUIDs
import com.example.gocantar.connectingthings.common.ids.TypeID

/**
 * Created by gocantar on 1/11/17.
 */

fun List<ParcelUuid>.getType(): TypeID {
    return when {
        find { UUIDs.Bulbs.contains(it.uuid) } != null -> TypeID.BULB
        find { UUIDs.Sensors.contains(it.uuid) } != null -> TypeID.SENSOR
        else -> TypeID.NO_TYPE
    }
}

