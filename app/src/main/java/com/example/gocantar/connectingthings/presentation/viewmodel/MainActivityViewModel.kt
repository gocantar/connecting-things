package com.example.gocantar.connectingthings.presentation.viewmodel

import android.app.Application
import com.example.gocantar.connectingthings.common.base.BaseViewModel
import com.example.gocantar.connectingthings.di.component.AppComponent
import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import javax.inject.Inject

/**
 * Created by gocantar on 17/10/17.
 */
class MainActivityViewModel(app: Application): BaseViewModel(app) {

    @Inject
    lateinit var mBLEServiceService: BLEServiceBoundary

    fun isBLEEnabled(): Boolean = mBLEServiceService.isBLEnabled()

    fun getRequestBLEIntent() = mBLEServiceService.getRequestBLEIntent()

    override fun setUpComponent(appComponent: AppComponent) {
       appComponent.inject(this)
    }
}