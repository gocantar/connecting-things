package com.gocantar.connectingthings.device.controller

import android.bluetooth.BluetoothGatt
import android.graphics.Color
import android.os.ParcelUuid
import com.gocantar.connectingthings.common.extension.getBulbServiceUuid
import com.gocantar.connectingthings.common.ids.ServicesUUIDs
import com.gocantar.connectingthings.device.light.BulbDeviceFactory
import com.gocantar.connectingthings.device.light.PlayBulbCandleDevice
import com.gocantar.connectingthings.device.light.RbpiBulbDevice
import com.gocantar.connectingthings.domain.boundary.BulbControllerBoundary
import com.gocantar.connectingthings.domain.entity.BulbParams
import com.gocantar.connectingthings.domain.entity.BulbStatus
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import io.reactivex.Observable

/**
 * Created by gocantar on 3/11/17.
 */
class BulbController: BulbControllerBoundary {

    private val TAG = javaClass.simpleName

    override fun setColor(params: BulbParams) {
        BulbDeviceFactory.createBulb(params.device.uuids.getBulbServiceUuid())
                .setColor(gatt = params.device.gattBluetoothGatt,
                        alpha = params.status.alpha,
                        red = Color.red(params.status.color),
                        green = Color.green(params.status.color),
                        blue = Color.blue(params.status.color))

    }

    override fun setEffect(params: BulbParams) {
        BulbDeviceFactory.createBulb(params.device.uuids.getBulbServiceUuid())
                .setEffect(gatt = params.device.gattBluetoothGatt,
                            alpha = params.status.alpha,
                            period = params.status.period,
                            red = Color.red(params.status.color),
                            green = Color.green(params.status.color),
                            blue = Color.blue(params.status.color),
                            effect = params.status.effectID)

    }

    override fun requestStatus(gatt: BluetoothGatt) {
        BulbDeviceFactory.createBulb(gatt.services.map { ParcelUuid(it.uuid) }.getBulbServiceUuid())
                    .readCharacteristic(gatt)

    }

    override fun decodeStatus(gatt: BluetoothGatt, charData: CharacteristicData): Observable<BulbStatus> {
        return BulbDeviceFactory.createBulb(gatt.services.map { ParcelUuid(it.uuid) }.getBulbServiceUuid())
           .decodeCharacteristic(charData)
    }

    override fun getAvailableEffects(): Observable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}