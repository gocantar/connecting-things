package com.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import com.gocantar.connectingthings.di.component.AppComponent
import com.gocantar.connectingthings.di.component.DaggerWeatherStationComponent
import com.gocantar.connectingthings.di.module.WeatherStationControllerModule

/**
 * Created by gocantar on 28/1/18.
 */
class WeatherStationViewModel(app: Application): BaseViewModel(app) {

    override fun setUpComponent(appComponent: AppComponent) {
        DaggerWeatherStationComponent.builder()
                .appComponent(appComponent)
                .weatherStationControllerModule(WeatherStationControllerModule(this))
                .build()
                .inject(this)
    }
}