package com.gocantar.connectingthings.di.module

import com.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.gocantar.connectingthings.domain.interactor.ConnectDevicesInteractor
import com.gocantar.connectingthings.domain.interactor.ScanDevicesInteractor
import com.gocantar.connectingthings.domain.usecase.ConnectDevicesActor
import com.gocantar.connectingthings.domain.usecase.ScanDevicesActor
import com.gocantar.connectingthings.presentation.viewmodel.ManageDevicesViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by gocantar on 17/10/17.
 */

@Module class ManageDevicesViewModelModule(manageDevicesViewModel: ManageDevicesViewModel){

    @Provides fun provideScannedDevicesActor(bleServiceService: BLEServiceBoundary): ScanDevicesInteractor = ScanDevicesActor(bleServiceService)

    @Provides fun provideConnectDevicesActor(bleService: BLEServiceBoundary): ConnectDevicesInteractor = ConnectDevicesActor(bleService)

}