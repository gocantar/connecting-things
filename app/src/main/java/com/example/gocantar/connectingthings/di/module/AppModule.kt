package com.example.gocantar.connectingthings.di.module

import android.bluetooth.BluetoothManager
import android.content.Context
import com.example.gocantar.connectingthings.AppController
import com.example.gocantar.connectingthings.data.controller.BLEController
import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.domain.interactor.Interactor
import com.example.gocantar.connectingthings.domain.interactor.ConnectDevicesInteractor
import com.example.gocantar.connectingthings.domain.usecase.ConnectDevicesActor
import com.example.gocantar.connectingthings.domain.usecase.GetConnectedDevicesActor
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

    @Provides fun provideGetConnectedDevices(bleService: BLEServiceBoundary): Interactor<BLEDevice> = GetConnectedDevicesActor(bleService)

    @Provides fun provideConnectDevices(bleService: BLEServiceBoundary): ConnectDevicesInteractor = ConnectDevicesActor(bleService)
}