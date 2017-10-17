package com.example.gocantar.connectingthings

import android.app.Application
import com.example.gocantar.connectingthings.di.component.AppComponent
import com.example.gocantar.connectingthings.di.module.AppModule
import com.example.gocantar.connectingthings.di.component.DaggerAppComponent



/**
 * Created by gocantar on 10/10/17.
 */
class AppController: Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var instance: AppController
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}