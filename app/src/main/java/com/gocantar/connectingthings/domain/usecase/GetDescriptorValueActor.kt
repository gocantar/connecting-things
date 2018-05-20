package com.gocantar.connectingthings.domain.usecase

import com.gocantar.connectingthings.common.enum.State
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorControllerBoundary
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by gocantar on 20/5/18.
 */
class GetDescriptorValueActor @Inject constructor(private val mSensorController: TemperatureSensorControllerBoundary):
        BaseInteractor<State, Unit>() {
    override fun buildUseCase(params: Unit): Observable<State> {
        return mSensorController.getNotificationState().distinctUntilChanged()
    }
}