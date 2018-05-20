package com.gocantar.connectingthings.di.module

import android.bluetooth.BluetoothManager
import android.content.Context
import com.gocantar.connectingthings.AppController
import com.gocantar.connectingthings.controller.BLEController
import com.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.gocantar.connectingthings.domain.entity.BLEDevice
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.entity.DeviceEvent
import com.gocantar.connectingthings.domain.usecase.*
import com.gocantar.connectingthings.data.datasource.FirebaseDataSource
import com.gocantar.connectingthings.data.repository.WeatherStationSensorRepository
import com.gocantar.connectingthings.device.controller.SensorController
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorControllerBoundary
import com.gocantar.connectingthings.domain.boundary.WeatherStationSensorRepositoryBoundary
import com.gocantar.connectingthings.domain.entity.SensorData
import com.gocantar.connectingthings.domain.interactor.DecodeCharacteristicDataInteractor
import com.gocantar.connectingthings.domain.interactor.SaveData
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by gocantar on 17/10/17.
 */
@Module class AppModule(val appController: AppController){

    @Provides @Singleton fun provideApp() = appController

    @Provides @Singleton fun provideBluetoothManager(): BluetoothManager =
            appController.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    @Provides @Singleton fun provideBLEService(bluetoothManager: BluetoothManager):
            BLEServiceBoundary = BLEController(bluetoothManager)

    @Provides @Singleton fun provideFirebaseDB():
            FirebaseDataSource = FirebaseDataSource()

    @Provides fun provideTemperatureSensorControllerBoundary(bleService: BLEServiceBoundary):
            TemperatureSensorControllerBoundary = SensorController(bleService)

    @Provides fun provideGetConnectedDevicesActor(bleService: BLEServiceBoundary):
            BaseInteractor<BLEDevice, Unit> = GetConnectedDevicesActor(bleService)

    @Provides fun provideGetCharacteristicReadActor(bleService: BLEServiceBoundary):
            BaseInteractor<CharacteristicData, Unit> = GetCharacteristicReadActor(bleService)

    @Provides fun provideGetBLENotificationsActor(bleService: BLEServiceBoundary):
            BaseInteractor<DeviceEvent, Unit> = GetBLENotificationsActor(bleService)

    @Provides fun provideDeviceActor(bleService: BLEServiceBoundary):
            BaseInteractor<BLEDevice, String> = GetDeviceActor(bleService)

    @Provides fun provideTemperatureSensorRepository(firebaseDB: FirebaseDataSource):
            WeatherStationSensorRepositoryBoundary = WeatherStationSensorRepository(firebaseDB)

    @Provides fun provideDecodeSensorData(weatherstationController: TemperatureSensorControllerBoundary):
            DecodeCharacteristicDataInteractor<SensorData?> = DecodeSensorDataActor(weatherstationController)

    @Provides fun provideSaveSensorData(weatherStationRepository: WeatherStationSensorRepositoryBoundary):
            SaveData<SensorData> = SaveDataSensorActor(weatherStationRepository)
}