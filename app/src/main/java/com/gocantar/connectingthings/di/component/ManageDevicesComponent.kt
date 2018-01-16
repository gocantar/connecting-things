package com.example.gocantar.connectingthings.di.component

import com.example.gocantar.connectingthings.di.module.ManageDevicesViewModelModule
import com.example.gocantar.connectingthings.di.scope.ModelViewScope
import com.example.gocantar.connectingthings.presentation.viewmodel.ManageDevicesViewModel
import dagger.Component

/**
 * Created by gocantar on 17/10/17.
 */

@ModelViewScope @Component (
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(ManageDevicesViewModelModule::class)
)

interface ManageDevicesComponent: AppComponent{
    fun inject (manageDevicesViewModel: ManageDevicesViewModel)
}