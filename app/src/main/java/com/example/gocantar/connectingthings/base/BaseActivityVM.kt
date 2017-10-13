package com.example.gocantar.connectingthings.base

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.PersistableBundle

/**
 * Created by gocantar on 6/10/17.
 */
abstract class BaseActivityVM<T: BaseViewModel>: BaseActivity() {

    abstract val mViewModelClass: Class<T>

    protected val mViewModel: T by lazy { ViewModelProviders.of(this).get(mViewModelClass) }

    private val mRegistry = LifecycleRegistry(this)

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        mViewModel.init()
    }

}