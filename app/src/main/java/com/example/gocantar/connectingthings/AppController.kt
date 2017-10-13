package com.example.gocantar.connectingthings

import android.app.Application

/**
 * Created by gocantar on 10/10/17.
 */
class AppController: Application() {

    companion object {
        lateinit var instance: AppController
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}