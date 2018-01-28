package com.gocantar.connectingthings.di.component

import com.gocantar.connectingthings.di.scope.ModelViewScope
import com.gocantar.connectingthings.di.module.WeatherStationControllerModule
import com.gocantar.connectingthings.presentation.viewmodel.WeatherStationViewModel
import dagger.Component

/**
 * Created by gocantar on 28/1/18.
 */

@ModelViewScope @Component(
        dependencies = [AppComponent::class],
        modules = [WeatherStationControllerModule::class]
)
interface WeatherStationComponent {
    fun inject (controlWeatherStations: WeatherStationViewModel)
}