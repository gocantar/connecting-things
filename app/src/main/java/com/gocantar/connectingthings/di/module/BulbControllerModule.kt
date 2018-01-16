package com.example.gocantar.connectingthings.di.module

import com.example.gocantar.connectingthings.device.controller.BulbController
import com.example.gocantar.connectingthings.domain.boundary.BulbControllerBoundary
import com.example.gocantar.connectingthings.domain.interactor.DecodeBulbCharacteristicInteractor
import com.example.gocantar.connectingthings.domain.interactor.ReadCharacteristicInteractor
import com.example.gocantar.connectingthings.domain.interactor.SetColorInteractor
import com.example.gocantar.connectingthings.domain.usecase.DecodeBulbCharacteristicActor
import com.example.gocantar.connectingthings.domain.usecase.ReadBulbStateActor
import com.example.gocantar.connectingthings.domain.usecase.SetBulbColorActor
import com.example.gocantar.connectingthings.presentation.viewmodel.ControlBulbViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by gocantar on 16/11/17.
 */
@Module class BulbControllerModule(controlBulbViewModel: ControlBulbViewModel) {

    @Provides fun provideBulbController():
            BulbControllerBoundary = BulbController()

    @Provides fun provideSetColorActor(bulbController: BulbControllerBoundary):
            SetColorInteractor = SetBulbColorActor(bulbController)

    @Provides fun provideReadBulbCharacteristicActor(bulbController: BulbControllerBoundary):
            ReadCharacteristicInteractor = ReadBulbStateActor(bulbController)

    @Provides fun provideDecodeBulbCharacteristicActor(bulbController: BulbControllerBoundary):
            DecodeBulbCharacteristicInteractor = DecodeBulbCharacteristicActor(bulbController)
}