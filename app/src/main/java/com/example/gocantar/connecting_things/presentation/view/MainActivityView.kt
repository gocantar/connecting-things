package com.example.gocantar.connecting_things.presentation.view

import android.os.Bundle
import com.example.gocantar.connecting_things.R
import com.example.gocantar.connecting_things.presentation.base.BaseActivity
import com.example.gocantar.connecting_things.presentation.base.BaseViewModel


class MainActivityView: BaseActivity<BaseViewModel>() {

    override val mViewModelClass: Class<BaseViewModel> = BaseViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
