package com.gocantar.connectingthings.data.repository

import com.example.gocantar.connectingthings.AppController
import com.example.gocantar.connectingthings.di.component.AppComponent
import com.gocantar.connectingthings.data.datasource.FirebaseDataSource
import com.gocantar.connectingthings.data.model.HumidityFB
import com.gocantar.connectingthings.data.model.TemperatureFB
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorRepositoryBoundary
import com.gocantar.connectingthings.domain.entity.HumidityParams
import com.gocantar.connectingthings.domain.entity.TemperatureParams
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by gocantar on 20/1/18.
 */
class TemperatureSensorRepository @Inject constructor(val mFirebaseDB: FirebaseDataSource) : TemperatureSensorRepositoryBoundary {

    override fun saveTemperature(temperatureParams: TemperatureParams) {
        mFirebaseDB.addTemperature(temperatureParams.address, TemperatureFB(temperatureParams.value, temperatureParams.timestamp))
    }

    override fun getTemperature(address: String, from: Long): Observable<TemperatureParams> {
        return mFirebaseDB.getTemperature(address, from).map { TemperatureParams(it.temperature, address, it.timestamp) }
    }

    override fun saveHumidity(humidityParams: HumidityParams) {
        mFirebaseDB.addHumidity(humidityParams.address, HumidityFB(humidityParams.value, humidityParams.timestamp))
    }

    override fun getHumidity(address: String, from: Long): Observable<HumidityParams> {
        return mFirebaseDB.getHumidity(address, from).map { HumidityParams(it.humidity, address, it.timestamp) }
    }


}