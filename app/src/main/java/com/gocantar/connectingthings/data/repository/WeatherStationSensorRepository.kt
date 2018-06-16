package com.gocantar.connectingthings.data.repository

import com.gocantar.connectingthings.data.datasource.FirebaseDataSource
import com.gocantar.connectingthings.data.model.HumidityFB
import com.gocantar.connectingthings.data.model.TemperatureFB
import com.gocantar.connectingthings.domain.boundary.WeatherStationSensorRepositoryBoundary
import com.gocantar.connectingthings.domain.entity.HumidityParams
import com.gocantar.connectingthings.domain.entity.TemperatureParams
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by gocantar on 20/1/18.
 */
class WeatherStationSensorRepository @Inject constructor(private val mFirebaseDB: FirebaseDataSource) : WeatherStationSensorRepositoryBoundary {

    override fun saveTemperature(temperatureParams: TemperatureParams) {
        mFirebaseDB.addTemperature(temperatureParams.address, TemperatureFB(temperatureParams.value, temperatureParams.timestamp))
    }

    override fun getTemperature(address: String, from: Long): Observable<TemperatureParams> {
        return mFirebaseDB.getTemperature(address, from).map { TemperatureParams(it.value, address, it.timestamp) }
    }

    override fun saveHumidity(humidityParams: HumidityParams) {
        mFirebaseDB.addHumidity(humidityParams.address, HumidityFB(humidityParams.value, humidityParams.timestamp))
    }

    override fun getHumidity(address: String, from: Long): Observable<HumidityParams> {
        return mFirebaseDB.getHumidity(address, from).map { HumidityParams(it.value, address, it.timestamp) }
    }


}