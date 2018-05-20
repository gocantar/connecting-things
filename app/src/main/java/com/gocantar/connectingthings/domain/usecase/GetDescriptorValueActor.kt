package com.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.common.enum.State
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorControllerBoundary
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by gocantar on 20/5/18.
 */
class GetDescriptorValueActor @Inject constructor(private val mSensorController: TemperatureSensorControllerBoundary):
        BaseInteractor<State, BluetoothGatt>() {
    override fun buildUseCase(params: BluetoothGatt): Observable<State> {
        return mSensorController.getNotificationState().take(1)
                .doOnSubscribe { mSensorController.requestNotificationsState(params) }
    }
}