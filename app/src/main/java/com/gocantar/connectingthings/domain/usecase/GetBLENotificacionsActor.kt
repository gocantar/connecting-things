package com.example.gocantar.connectingthings.domain.usecase

import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.domain.entity.DeviceEvent
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by gocantar on 2/1/18.
 */
class GetBLENotificacionsActor @Inject constructor(private val mBLEService: BLEServiceBoundary): BaseInteractor<DeviceEvent, Unit>() {
    override fun buildUseCase(params: Unit): Observable<DeviceEvent> = mBLEService.mPublisherOfEvent
}