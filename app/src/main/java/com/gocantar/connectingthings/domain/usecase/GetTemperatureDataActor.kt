package com.gocantar.connectingthings.domain.usecase

import android.os.Bundle
import com.gocantar.connectingthings.common.Constants
import com.gocantar.connectingthings.domain.boundary.WeatherStationSensorRepositoryBoundary
import com.gocantar.connectingthings.domain.entity.TemperatureParams
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by gocantar on 6/2/18.
 */
class GetTemperatureDataActor @Inject constructor(private val mSensorRepository: WeatherStationSensorRepositoryBoundary):
        BaseInteractor<TemperatureParams, Bundle>() {
    override fun buildUseCase(params: Bundle): Observable<TemperatureParams> {
        val address: String = params.getString(Constants.ADDRESS)
        val timestamp = params.getLong(Constants.TIMESTAMP, 0)
        return mSensorRepository.getTemperature(address, timestamp)
    }
}