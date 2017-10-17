package com.example.gocantar.connectingthings.di.component

import android.bluetooth.BluetoothManager
import com.example.gocantar.connectingthings.di.module.AppModule
import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.presentation.viewmodel.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by gocantar on 17/10/17.
 */

@Singleton @Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun getBluetoothManager(): BluetoothManager
    fun getBLEService(): BLEServiceBoundary

    fun inject(mainActivityViewModel: MainActivityViewModel)
}