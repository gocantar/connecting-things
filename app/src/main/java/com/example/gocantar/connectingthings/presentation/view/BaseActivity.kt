package com.example.gocantar.connectingthings.presentation.view

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * Created by gocantar on 10/10/17.
 */
abstract class BaseActivity: AppCompatActivity(){

    protected val TAG: String by lazy { this::class.java.simpleName }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.i(TAG, "onCreate:")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy:")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart:")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume:")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause:")
    }
}