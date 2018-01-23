package com.example.gocantar.connectingthings.common.extension

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


fun List<ParcelUuid>.getBulbServiceUuid(): ParcelUuid = first { ServicesUUIDs.Bulbs.contains(it.uuid) }

fun List<ParcelUuid>.getPlugServiceUuid(): ParcelUuid = first{ ServicesUUIDs.Plugs.contains(it.uuid) }

fun List<ParcelUuid>.getSensorServiceUuid(): ParcelUuid = first { ServicesUUIDs.Sensors.contains(it.uuid) }







