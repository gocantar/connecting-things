package com.gocantar.connectingthings.di.module

import android.os.Bundle
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorControllerBoundary
import com.gocantar.connectingthings.domain.boundary.WeatherStationSensorRepositoryBoundary
import com.gocantar.connectingthings.domain.entity.HumidityParams
import com.gocantar.connectingthings.domain.entity.TemperatureParams
import com.gocantar.connectingthings.domain.usecase.*
import com.gocantar.connectingthings.presentation.viewmodel.WeatherStationViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by gocantar on 28/1/18.
 */
@Module class WeatherStationControllerModule(controllerModule: WeatherStationViewModel) {

    @Provides fun provideManageNotificationsActor(sensorController: TemperatureSensorControllerBoundary):
            ManageSensorNotificationsActor = ManageSensorNotificationsActor(sensorController)

    @Provides fun provideRequestNotificationsStateActor(sensorController: TemperatureSensorControllerBoundary):
            GetDescriptorValueActor = GetDescriptorValueActor(sensorController)

    @Provides fun provideGetTemperatureDataActor(repository: WeatherStationSensorRepositoryBoundary):
            BaseInteractor<TemperatureParams, Bundle> = GetTemperatureDataActor(repository)

    @Provides fun provideGetHumidityDataActor(repository: WeatherStationSensorRepositoryBoundary):
            BaseInteractor<HumidityParams, Bundle> = GetHumidityDataActor(repository)

    @Provides fun provideRequestDescriptorValue(sensorController: TemperatureSensorControllerBoundary):
            RequestDescriptorValue = RequestDescriptorValue(sensorController)

}