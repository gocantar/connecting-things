package com.gocantar.connectingthings.device.light

import android.os.ParcelUuid
import com.gocantar.connectingthings.common.ids.ServicesUUIDs

/**
 * Created by gocantar on 11/3/18.
 */
object BulbDeviceFactory {

    fun createBulb(uuid: ParcelUuid) :BulbDevice{
        return when(uuid){
            ParcelUuid(ServicesUUIDs.PLAYBULB_CANDLE_PRIMARY_SERVICE) -> PlayBulbCandleDevice()
            else -> RbpiBulbDevice()
        }
    }

}