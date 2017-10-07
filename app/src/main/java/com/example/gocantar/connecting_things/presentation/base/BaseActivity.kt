package com.example.gocantar.connecting_things.presentation.base

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * Created by gocantar on 6/10/17.
 */
abstract class BaseActivity<T: BaseViewModel>: AppCompatActivity() {

    private val TAG: String by lazy { this::class.java.simpleName }

    abstract val mViewModelClass: Class<T>

    protected val mViewModel: T by lazy { ViewModelProviders.of(this).get(mViewModelClass) }

    private val mRegistry = LifecycleRegistry(this)

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.i(TAG, "onCreate:")
        mViewModel.init()
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