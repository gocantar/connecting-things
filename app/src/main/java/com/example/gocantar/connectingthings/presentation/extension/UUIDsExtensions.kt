package com.example.gocantar.connectingthings.presentation.extension

import android.os.ParcelUuid
import com.example.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.example.gocantar.connectingthings.common.ids.TypeID

/**
 * Created by gocantar on 1/11/17.
 */

fun List<ParcelUuid>.getType(): TypeID {
    return when {
        find { ServicesUUIDs.Bulbs.contains(it.uuid) } != null -> TypeID.BULB
        find { ServicesUUIDs.Plugs.contains(it.uuid) } != null -> TypeID.PLUG
        find { ServicesUUIDs.Sensors.contains(it.uuid) } != null -> TypeID.SENSOR
        else -> TypeID.NO_TYPE
    }
}

