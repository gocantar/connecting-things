package com.example.gocantar.connectingthings.di.module

import android.bluetooth.BluetoothManager
import android.content.Context
import com.example.gocantar.connectingthings.AppController
import com.example.gocantar.connectingthings.data.controller.BLEController
import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.domain.entity.CharacteristicData
import com.example.gocantar.connectingthings.domain.entity.DeviceEvent
import com.example.gocantar.connectingthings.domain.usecase.*
import com.gocantar.connectingthings.data.datasource.FirebaseDataSource
import com.gocantar.connectingthings.data.repository.TemperatureSensorRepository
import com.gocantar.connectingthings.domain.boundary.TemperatureSensorRepositoryBoundary
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by gocantar on 17/10/17.
 */
@Module class AppModule(val appController: AppController){

    @Provides @Singleton fun provideApp() = appController

    @Provides @Singleton fun provideBluetoothManager(): BluetoothManager = appController.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    @Provides @Singleton fun provideBLEService(bluetoothManager: BluetoothManager): BLEServiceBoundary = BLEController(bluetoothManager)

    @Provides @Singleton fun provideFirebaseDB(): FirebaseDataSource = FirebaseDataSource()

    @Provides fun provideGetConnectedDevicesActor(bleService: BLEServiceBoundary): BaseInteractor<BLEDevice, Unit> = GetConnectedDevicesActor(bleService)

    @Provides fun provideGetCharacteristicNotificationActor(bleService: BLEServiceBoundary): BaseInteractor<CharacteristicData, Unit> = GetCharacteristicNotificationActor(bleService)

    @Provides fun provideGetBLENotificationsActor(bleService: BLEServiceBoundary): BaseInteractor<DeviceEvent, Unit> = GetBLENotificacionsActor(bleService)

    @Provides fun provideDeviceActor(bleService: BLEServiceBoundary): BaseInteractor<BLEDevice, String> = GetDeviceActor(bleService)

    @Provides fun privideTemperatureSensorRepository(firebaseDB: FirebaseDataSource): TemperatureSensorRepositoryBoundary  = TemperatureSensorRepository(firebaseDB)

}