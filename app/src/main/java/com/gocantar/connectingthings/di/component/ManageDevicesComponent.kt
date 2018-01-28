package com.gocantar.connectingthings.di.component

import com.gocantar.connectingthings.di.module.ManageDevicesViewModelModule
import com.gocantar.connectingthings.di.scope.ModelViewScope
import com.gocantar.connectingthings.presentation.viewmodel.ManageDevicesViewModel
import dagger.Component

/**
 * Created by gocantar on 17/10/17.
 */

@ModelViewScope @Component (
        dependencies = [(AppComponent::class)],
        modules = [(ManageDevicesViewModelModule::class)]
)

interface ManageDevicesComponent: AppComponent{
    fun inject (manageDevicesViewModel: ManageDevicesViewModel)
}