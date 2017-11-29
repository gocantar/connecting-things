package com.example.gocantar.connectingthings.di.component

import com.example.gocantar.connectingthings.di.module.PlugControllerModule
import com.example.gocantar.connectingthings.di.scope.ModelViewScope
import com.example.gocantar.connectingthings.presentation.viewmodel.ControlPlugViewModel
import dagger.Component

/**
 * Created by gocantar on 29/11/17.
 */
@ModelViewScope @Component (
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(PlugControllerModule::class)
)

interface PlugControllerComponent: AppComponent {
    fun inject (controlPlugViewModel: ControlPlugViewModel)
}