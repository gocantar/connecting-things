package com.example.gocantar.connectingthings.di.module

import android.bluetooth.BluetoothManager
import android.content.Context
import com.example.gocantar.connectingthings.AppController
import com.example.gocantar.connectingthings.data.BLEService
import com.example.gocantar.connectingthings.domain.boundary.BLEBoundary
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by gocantar on 17/10/17.
 */
@Module class AppModule(val appController: AppController){

    @Provides @Singleton fun provideApp() = appController

    @Provides @Singleton fun provideBluetoothManager(): BluetoothManager = appController.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    @Provides @Singleton fun provideBLEService(bluetoothManager: BluetoothManager): BLEBoundary = BLEService(bluetoothManager)
}