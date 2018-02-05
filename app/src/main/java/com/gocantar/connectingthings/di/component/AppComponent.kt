package com.gocantar.connectingthings.di.component

import com.gocantar.connectingthings.di.module.AppModule
import com.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.gocantar.connectingthings.presentation.viewmodel.MainActivityViewModel
import com.gocantar.connectingthings.data.repository.TemperatureSensorRepository
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorControllerBoundary
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorRepositoryBoundary
import dagger.Component
import javax.inject.Singleton

/**
 * Created by gocantar on 17/10/17.
 */

@Singleton @Component(modules = arrayOf(AppModule::class))

interface AppComponent {

    fun getBLEService(): BLEServiceBoundary
    fun getTemperatureSensorController(): TemperatureSensorControllerBoundary
    fun getTemperatureSensorRepository(): TemperatureSensorRepositoryBoundary

    fun inject(mainActivityViewModel: MainActivityViewModel)
    fun inject(temperatureRepository: TemperatureSensorRepository)
}