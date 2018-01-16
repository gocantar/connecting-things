package com.example.gocantar.connectingthings.di.module

import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.domain.interactor.ConnectDevicesInteractor
import com.example.gocantar.connectingthings.domain.interactor.ScanDevicesInteractor
import com.example.gocantar.connectingthings.domain.usecase.ConnectDevicesActor
import com.example.gocantar.connectingthings.domain.usecase.ScanDevicesActor
import com.example.gocantar.connectingthings.presentation.viewmodel.ManageDevicesViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by gocantar on 17/10/17.
 */

@Module class ManageDevicesViewModelModule(manageDevicesViewModel: ManageDevicesViewModel){

    @Provides fun provideScannedDevicesActor(bleServiceService: BLEServiceBoundary): ScanDevicesInteractor = ScanDevicesActor(bleServiceService)

    @Provides fun provideConnectDevicesActor(bleService: BLEServiceBoundary): ConnectDevicesInteractor = ConnectDevicesActor(bleService)

}