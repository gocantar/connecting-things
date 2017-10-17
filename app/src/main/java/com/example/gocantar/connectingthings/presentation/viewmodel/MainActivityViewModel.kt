package com.example.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import com.example.gocantar.connectingthings.common.base.BaseViewModel
import com.example.gocantar.connectingthings.di.component.AppComponent
import com.example.gocantar.connectingthings.domain.boundary.BLEBoundary
import javax.inject.Inject

/**
 * Created by gocantar on 17/10/17.
 */
class MainActivityViewModel(app: Application): BaseViewModel(app) {

    @Inject
    lateinit var mBLEService: BLEBoundary

    fun isBLEEnabled(): Boolean = mBLEService.isBLEnabled()

    fun getRequestBLEIntent() = mBLEService.getRequestBLEIntent()

    override fun setUpComponent(appComponent: AppComponent) {
       appComponent.inject(this)
    }
}