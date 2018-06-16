package com.gocantar.connectingthings.di.component

import com.gocantar.connectingthings.di.module.BulbControllerModule
import com.gocantar.connectingthings.di.scope.ModelViewScope
import com.gocantar.connectingthings.presentation.viewmodel.ControlBulbViewModel
import dagger.Component

/**
 * Created by gocantar on 16/11/17.
 */

@ModelViewScope @Component (
        dependencies = [(AppComponent::class)],
        modules = [(BulbControllerModule::class)]
)

interface BulbControllerComponent : AppComponent{
    fun inject (controlBulbViewModel: ControlBulbViewModel)
}