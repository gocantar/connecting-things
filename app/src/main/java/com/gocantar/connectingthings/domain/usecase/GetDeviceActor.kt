package com.gocantar.connectingthings.domain.usecase

import com.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.gocantar.connectingthings.domain.entity.BLEDevice
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by gocantar on 16/11/17.
 */
class GetDeviceActor @Inject constructor(private val mBLEService: BLEServiceBoundary): BaseInteractor<BLEDevice, String>(){

    override fun buildUseCase(params: String): Observable<BLEDevice> =
            mBLEService.getDevice(params)

}