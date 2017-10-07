package com.example.gocantar.connecting_things.presentation.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.util.Log

/**
 * Created by gocantar on 10/7/17.
 */
abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    private val TAG :String by lazy { this::class.java.simpleName }

    fun init() {
        Log.i(TAG, "initiated")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared")
    }
}