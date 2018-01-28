package com.gocantar.connectingthings.di.component

import com.gocantar.connectingthings.di.module.PlugControllerModule
import com.gocantar.connectingthings.di.scope.ModelViewScope
import com.gocantar.connectingthings.presentation.viewmodel.ControlPlugViewModel
import dagger.Component

/**
 * Created by gocantar on 29/11/17.
 */
@ModelViewScope @Component (
        dependencies = [(AppComponent::class)],
        modules = [(PlugControllerModule::class)]
)

interface PlugControllerComponent: AppComponent {
    fun inject (controlPlugViewModel: ControlPlugViewModel)
}