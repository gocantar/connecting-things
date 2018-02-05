package com.gocantar.connectingthings.domain.usecase

import com.gocantar.connectingthings.common.enum.SensorType
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorRepositoryBoundary
import com.gocantar.connectingthings.domain.entity.HumidityParams
import com.gocantar.connectingthings.domain.entity.SensorData
import com.gocantar.connectingthings.domain.entity.TemperatureParams
import com.gocantar.connectingthings.domain.interactor.SaveData
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by gocantar on 5/2/18.
 */

class SaveDataSensorActor @Inject constructor(private val mSensorRepository: TemperatureSensorRepositoryBoundary): SaveData<SensorData> {
    override fun save(data: SensorData) {
        doAsync {
            when(data.type){
                SensorType.TEMPERATURE -> saveTemperature(data)
                SensorType.HUMIDITY -> saveHumidity(data)
            }
        }
    }

    private fun saveTemperature(temperatureData: SensorData){
        mSensorRepository.saveTemperature(TemperatureParams(temperatureData.value, temperatureData.address))
    }

    private fun saveHumidity(humidityData: SensorData){
        mSensorRepository.saveHumidity(HumidityParams(humidityData.value, humidityData.address))
    }
}