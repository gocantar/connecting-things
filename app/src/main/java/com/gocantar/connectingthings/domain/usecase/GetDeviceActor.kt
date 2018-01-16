package com.example.gocantar.connectingthings.domain.usecase

import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by gocantar on 16/11/17.
 */
class GetDeviceActor @Inject constructor(private val mBLEService: BLEServiceBoundary): BaseInteractor<BLEDevice, String>(){

    override fun buildUseCase(params: String): Observable<BLEDevice> =
            mBLEService.getDevice(params)

}