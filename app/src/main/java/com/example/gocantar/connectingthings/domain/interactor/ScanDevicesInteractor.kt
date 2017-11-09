package com.example.gocantar.connectingthings.domain.interactor

import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import io.reactivex.observers.DisposableObserver

/**
 * Created by gocantar on 2/11/17.
 */
interface ScanDevicesInteractor {
    fun start(disposable: DisposableObserver<BLEDevice>)
    fun stop()
    fun dispose()
}