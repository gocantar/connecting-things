package com.gocantar.connectingthings.presentation.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.gocantar.connectingthings.presentation.viewmodel.BaseViewModel

/**
 * Created by gocantar on 6/10/17.
 */
abstract class BaseActivityVM<T: BaseViewModel>: BaseActivity() {

    abstract val mViewModelClass: Class<T>

    protected val mViewModel: T by lazy { ViewModelProviders.of(this).get(mViewModelClass) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.init()
    }

}