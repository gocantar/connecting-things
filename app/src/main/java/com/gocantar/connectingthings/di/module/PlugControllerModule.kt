package com.example.gocantar.connectingthings.di.module

import com.example.gocantar.connectingthings.device.controller.PlugController
import com.example.gocantar.connectingthings.domain.boundary.PlugControllerBoundary
import com.example.gocantar.connectingthings.domain.interactor.DecodeLivePowerConsumptionInteractor
import com.example.gocantar.connectingthings.domain.interactor.ManagePlugNotificationsInteractor
import com.example.gocantar.connectingthings.domain.interactor.RequestLivePowerConsumptionInteractor
import com.example.gocantar.connectingthings.domain.interactor.SetPlugStateInteractor
import com.example.gocantar.connectingthings.domain.usecase.*
import com.example.gocantar.connectingthings.presentation.viewmodel.ControlPlugViewModel
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
            ManagePlugNotificationsInteractor = ManagePlugNotificationsActor(plugController)

    @Provides fun provideDecodeLivePowerConsumptionActor(plugController: PlugControllerBoundary):
            DecodeLivePowerConsumptionInteractor = DecodeLivePowerConsumptionActor(plugController)
}