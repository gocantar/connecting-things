package com.gocantar.connectingthings.domain.usecase

import android.os.Bundle
import com.gocantar.connectingthings.common.Constants
import com.gocantar.connectingthings.domain.boundary.WeatherStationSensorRepositoryBoundary
import com.gocantar.connectingthings.domain.entity.HumidityParams
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by gocantar on 15/5/18.
 */
class GetHumidityDataActor @Inject constructor(private val mSensorRepository: WeatherStationSensorRepositoryBoundary):
        BaseInteractor<HumidityParams, Bundle>(){
    override fun buildUseCase(params: Bundle): Observable<HumidityParams> {
        val address: String = params.getString(Constants.ADDRESS)
        val timestamp: Long = params.getLong(Constants.TIMESTAMP)
        return mSensorRepository.getHumidity(address, timestamp)
    }
}