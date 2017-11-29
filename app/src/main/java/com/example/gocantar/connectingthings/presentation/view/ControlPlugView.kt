package com.example.gocantar.connectingthings.presentation.view

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.common.ids.Key
import com.example.gocantar.connectingthings.presentation.viewmodel.ControlPlugViewModel
import kotlinx.android.synthetic.main.activity_plug_controller.*

/**
 * Created by gocantar on 29/11/17.
 */
class ControlPlugView: BaseActivityVM<ControlPlugViewModel>() {

    override val mViewModelClass: Class<ControlPlugViewModel> = ControlPlugViewModel::class.java


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plug_controller)

        pa_title.setOnClickListener { onBackPressed() }

        mViewModel.pa_title.observe(this, Observer { pa_title.text = it })

        mViewModel.initialize(intent.extras.getString(Key.DEVICE_ADDRESS))
    }


    /**
     * Statics
     */
    companion object {

        val REQUEST_CODE = 10300

        fun getCallingIntent(context: Context, address: String): Intent{
            val intent = Intent(context, ControlPlugView::class.java)
            intent.putExtra(Key.DEVICE_ADDRESS, address)
            return intent
        }

    }
}