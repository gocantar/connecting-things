package com.gocantar.connectingthings.presentation.view

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.common.ids.Key
import com.gocantar.connectingthings.presentation.extension.animate
import com.gocantar.connectingthings.presentation.viewmodel.ControlPlugViewModel
import kotlinx.android.synthetic.main.activity_plug_controller.*

/**
 * Created by gocantar on 29/11/17.
 */
class ControlPlugView: BaseActivityVM<ControlPlugViewModel>() {

    override val mViewModelClass: Class<ControlPlugViewModel> = ControlPlugViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plug_controller)

        setUpButtonListeners()
        setUpDataBinding()

        mViewModel.initialize(intent.extras.getString(Key.DEVICE_ADDRESS))
    }

    private fun setUpDataBinding(){
        mViewModel.mTitle.observe(this, Observer { pa_title.text = it })
        mViewModel.mPlugState.observe(this, Observer {
            pa_state.text = it?.second
            pa_state.setTextColor(it?.first!!)
        })
        mViewModel.mPowerConsumption.observe(this, Observer {
            pa_live_power_consumption.text = it
            pa_live_power_consumption_progress.text = it
        })
        mViewModel.mPowerConsumptionProgress.observe(this, Observer {
            pa_progress_power_consumption.animate(it!!)
        })
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

    private fun setUpButtonListeners(){
        pa_title.setOnClickListener { onBackPressed() }
        pa_turn_on_button.setOnClickListener { mViewModel.turnOn() }
        pa_turn_off_button.setOnClickListener { mViewModel.turnOff() }
    }
}