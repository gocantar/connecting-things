package com.gocantar.connectingthings.domain.usecase

import com.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.gocantar.connectingthings.domain.entity.BLEDevice
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by gocantar on 2/11/17.
 */

class GetConnectedDevicesActor @Inject constructor(private val mBLEService: BLEServiceBoundary): BaseInteractor<BLEDevice, Unit>(){
    override fun buildUseCase(params: Unit): Observable<BLEDevice> =
         mBLEService.getConnectedDevices()
}