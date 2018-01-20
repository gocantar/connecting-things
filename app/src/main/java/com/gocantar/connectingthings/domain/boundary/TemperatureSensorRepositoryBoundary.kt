package com.gocantar.connectingthings.domain.boundary

import com.gocantar.connectingthings.domain.entity.HumidityParams
import com.gocantar.connectingthings.domain.entity.TemperatureParams
import io.reactivex.Observable

/**
 * Created by gocantar on 20/1/18.
 */
interface TemperatureSensorRepositoryBoundary {
    fun saveTemperature(temperatureParams: TemperatureParams)
    fun getTemperature(address: String, from: Long = 0): Observable<TemperatureParams>
    fun saveHumidity(humidityParams: HumidityParams)
    fun getHumidity(address: String, from: Long = 0): Observable<HumidityParams>
}