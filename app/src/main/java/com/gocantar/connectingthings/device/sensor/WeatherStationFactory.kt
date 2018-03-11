package com.gocantar.connectingthings.device.sensor

import android.os.ParcelUuid

/**
 * Created by gocantar on 11/3/18.
 */
object WeatherStationFactory {

    fun createWeatherStation(uuid: ParcelUuid): WeatherStation{
        return WeatherStationArduino101()
    }
}