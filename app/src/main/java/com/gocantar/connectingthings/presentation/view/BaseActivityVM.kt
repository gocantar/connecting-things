package com.gocantar.connectingthings.presentation.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.presentation.viewmodel.BaseViewModel
import org.jetbrains.anko.backgroundColor

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

    fun showErrorSnackBar(message: String, container: View){
        val snackBar = Snackbar.make(container, message, Snackbar.LENGTH_LONG)
        snackBar.view.backgroundColor = resources.getColor(R.color.redWrong, application.theme)
        snackBar.show()
    }

}