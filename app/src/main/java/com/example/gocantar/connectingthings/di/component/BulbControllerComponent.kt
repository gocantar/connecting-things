package com.example.gocantar.connectingthings.di.component

import com.example.gocantar.connectingthings.di.module.BulbControllerModule
import com.example.gocantar.connectingthings.di.scope.ModelViewScope
import com.example.gocantar.connectingthings.presentation.viewmodel.ControlBulbViewModel
import dagger.Component

/**
 * Created by gocantar on 16/11/17.
 */

@ModelViewScope @Component (
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(BulbControllerModule::class)
)

interface BulbControllerComponent : AppComponent{
    fun inject (controlBulbViewModel: ControlBulbViewModel)
}