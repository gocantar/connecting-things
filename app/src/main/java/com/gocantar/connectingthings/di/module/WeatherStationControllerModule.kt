package com.gocantar.connectingthings.di.module

import com.gocantar.connectingthings.device.controller.SensorController
import com.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorControllerBoundary
import com.gocantar.connectingthings.domain.usecase.ManageSensorNotificationsActor
import com.gocantar.connectingthings.presentation.viewmodel.WeatherStationViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by gocantar on 28/1/18.
 */
@Module class WeatherStationControllerModule(controllerModule: WeatherStationViewModel) {

    @Provides fun provideManageNotificationsActor(sensorController: TemperatureSensorControllerBoundary):
            ManageSensorNotificationsActor = ManageSensorNotificationsActor(sensorController)

}