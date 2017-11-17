package com.example.gocantar.connectingthings.di.module

import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import com.example.gocantar.connectingthings.domain.usecase.GetDeviceActor
import com.example.gocantar.connectingthings.domain.usecase.BaseInteractor
import com.example.gocantar.connectingthings.presentation.viewmodel.ControlBulbViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by gocantar on 16/11/17.
 */
@Module class BulbControllerModule(controlBulbViewModel: ControlBulbViewModel) {

    @Provides fun provideDevice(bleService: BLEServiceBoundary): BaseInteractor<BLEDevice, String> = GetDeviceActor(bleService)

}