package com.gocantar.connectingthings.domain.boundary

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.common.enum.State
import io.reactivex.Observable

/**
 * Created by gocantar on 23/1/18.
 */
interface ManageNotificationsBoundary {
    fun enableNotifications(gatt: BluetoothGatt)
    fun disableNotifications(gatt: BluetoothGatt)
}