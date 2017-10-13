package com.example.gocantar.connectingthings.presentation.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.base.BaseActivityVM
import com.example.gocantar.connectingthings.presentation.viewmodel.ManageDevicesViewModel
import kotlinx.android.synthetic.main.activity_manage_devices.*

/**
 * Created by gocantar on 10/10/17.
 */
class ManageDevicesView : BaseActivityVM<ManageDevicesViewModel>() {

    override val mViewModelClass: Class<ManageDevicesViewModel> = ManageDevicesViewModel::class.java

    /**
     * Override functions
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_devices)

        md_back_button.setOnClickListener{ onBackPressed() }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.startScanDevices()
    }

    override fun onPause() {
        super.onPause()
        mViewModel.stopScanDevices()
    }

    companion object {
        fun getCallingIntent(context: Context) : Intent {
            return Intent(context, ManageDevicesView::class.java)
        }
    }

}