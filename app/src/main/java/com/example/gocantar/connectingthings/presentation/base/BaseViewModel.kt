package com.example.gocantar.connectingthings.presentation.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.util.Log
import com.example.gocantar.connectingthings.AppController
import com.example.gocantar.connectingthings.di.component.AppComponent

/**
 * Created by gocantar on 10/7/17.
 */
abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    protected val TAG :String by lazy { this::class.java.simpleName }

    protected val appComponent: AppComponent by lazy { (application as AppController).component }

    protected val mResources = AppController.instance.resources

    fun init() {
        Log.i(TAG, "initiated")
        setUpComponent(appComponent)
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared")
    }

    abstract fun setUpComponent(appComponent: AppComponent)

}