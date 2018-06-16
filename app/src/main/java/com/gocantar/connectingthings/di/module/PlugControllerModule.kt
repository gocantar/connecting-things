package com.gocantar.connectingthings.di.module

import com.gocantar.connectingthings.device.controller.PlugController
import com.gocantar.connectingthings.domain.boundary.PlugControllerBoundary
import com.gocantar.connectingthings.domain.interactor.DecodeCharacteristicDataInteractor
import com.gocantar.connectingthings.domain.interactor.ManageNotificationsInteractor
import com.gocantar.connectingthings.domain.interactor.RequestLivePowerConsumptionInteractor
import com.gocantar.connectingthings.domain.interactor.SetPlugStateInteractor
import com.gocantar.connectingthings.domain.usecase.DecodeLivePowerConsumptionActor
import com.gocantar.connectingthings.domain.usecase.ManagePlugNotificationsActor
import com.gocantar.connectingthings.domain.usecase.RequestLivePowerConsumptionActor
import com.gocantar.connectingthings.domain.usecase.SetPlugStateActor
import com.gocantar.connectingthings.presentation.viewmodel.ControlPlugViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by gocantar on 29/11/17.
 */
@Module class PlugControllerModule (controllerModule: ControlPlugViewModel) {

    @Provides fun providePlugController():
            PlugControllerBoundary = PlugController()

    @Provides fun provideSetPlugStatusActor(plugController: PlugControllerBoundary):
            SetPlugStateInteractor = SetPlugStateActor(plugController)

    @Provides fun provideRequestLivePowerConsumptionActor(plugController: PlugControllerBoundary):
            RequestLivePowerConsumptionInteractor = RequestLivePowerConsumptionActor(plugController)

    @Provides fun provideManageNotificationsActor(plugController: PlugControllerBoundary):
            ManageNotificationsInteractor = ManagePlugNotificationsActor(plugController)

    @Provides fun provideDecodeLivePowerConsumptionActor(plugController: PlugControllerBoundary):
            DecodeCharacteristicDataInteractor<Int> = DecodeLivePowerConsumptionActor(plugController)
}