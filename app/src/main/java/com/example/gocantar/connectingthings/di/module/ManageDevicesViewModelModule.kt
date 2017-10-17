package com.example.gocantar.connectingthings.di.module

import com.example.gocantar.connectingthings.domain.boundary.BLEBoundary
import com.example.gocantar.connectingthings.domain.usecase.ScanDevicesActor
import com.example.gocantar.connectingthings.presentation.viewmodel.ManageDevicesViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by gocantar on 17/10/17.
 */

@Module class ManageDevicesViewModelModule(manageDevicesViewModel: ManageDevicesViewModel){

    @Provides fun provideScannedDevices(bleService: BLEBoundary): ScanDevicesActor = ScanDevicesActor(bleService)

}