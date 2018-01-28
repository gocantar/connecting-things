package com.gocantar.connectingthings.di.component

import android.bluetooth.BluetoothManager
import com.gocantar.connectingthings.di.module.AppModule
import com.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.gocantar.connectingthings.domain.entity.BLEDevice
import com.gocantar.connectingthings.domain.usecase.BaseInteractor
import com.gocantar.connectingthings.presentation.viewmodel.MainActivityViewModel
import com.gocantar.connectingthings.data.repository.TemperatureSensorRepository
import dagger.Component
import javax.inject.Singleton

/**
 * Created by gocantar on 17/10/17.
 */

@Singleton @Component(modules = arrayOf(AppModule::class))

interface AppComponent {

    fun getBluetoothManager(): BluetoothManager
    fun getBLEService(): BLEServiceBoundary
    fun getConnectedDevicesInteractor(): BaseInteractor<BLEDevice, Unit>

    fun inject(mainActivityViewModel: MainActivityViewModel)
    fun inject(temperatureRepository: TemperatureSensorRepository)
}