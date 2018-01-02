package com.example.gocantar.connectingthings.di.module

import com.example.gocantar.connectingthings.device.controller.BulbController
import com.example.gocantar.connectingthings.domain.boundary.BulbControllerBoundary
import com.example.gocantar.connectingthings.domain.interactor.SetColorInteractor
import com.example.gocantar.connectingthings.domain.usecase.SetColorActor
import com.example.gocantar.connectingthings.presentation.viewmodel.ControlBulbViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by gocantar on 16/11/17.
 */
@Module class BulbControllerModule(controlBulbViewModel: ControlBulbViewModel) {

    @Provides fun provideBulbController(): BulbControllerBoundary = BulbController()

    @Provides fun provideSetColorActor(bulbController: BulbControllerBoundary): SetColorInteractor = SetColorActor(bulbController)
}