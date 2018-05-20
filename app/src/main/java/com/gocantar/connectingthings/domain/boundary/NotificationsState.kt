package com.gocantar.connectingthings.domain.boundary

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.common.enum.State
import io.reactivex.Observable

/**
 * Created by gocantar on 20/5/18.
 */
interface NotificationsState {
    fun getNotificationState(): Observable<State>
    fun requestNotificationsState(gatt: BluetoothGatt)
}