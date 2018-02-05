package com.gocantar.connectingthings.common.extension

import android.os.ParcelUuid
import com.gocantar.connectingthings.common.ids.BLEServicesUUIDs
import com.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.gocantar.connectingthings.common.ids.TypeID
import java.util.*

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

fun UUID.getServiceUuidFromCharacteristic(): UUID{
    return BLEServicesUUIDs.SENSORS_BLE_SERVICES.filter { it.value.contains(this) }.keys.first()
}


fun List<ParcelUuid>.getBulbServiceUuid(): ParcelUuid = first { ServicesUUIDs.Bulbs.contains(it.uuid) }

fun List<ParcelUuid>.getPlugServiceUuid(): ParcelUuid = first{ ServicesUUIDs.Plugs.contains(it.uuid) }

fun List<ParcelUuid>.getSensorServiceUuid(): ParcelUuid = first { ServicesUUIDs.Sensors.contains(it.uuid) }







