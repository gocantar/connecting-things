package com.gocantar.connectingthings.di.module

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.device.controller.BulbController
import com.gocantar.connectingthings.domain.boundary.BulbControllerBoundary
import com.gocantar.connectingthings.domain.interactor.DecodeBulbCharacteristicInteractor
import com.gocantar.connectingthings.domain.interactor.ReadCharacteristicInteractor
import com.gocantar.connectingthings.domain.interactor.SetColorInteractor
import com.gocantar.connectingthings.domain.usecase.*
import com.gocantar.connectingthings.presentation.viewmodel.ControlBulbViewModel
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

    @Provides fun provideGetAvailableBulbEffectsActor(bulbController: BulbControllerBoundary):
            BaseInteractor<Int, BluetoothGatt> = GetAvailableBulbEffects(bulbController)
}