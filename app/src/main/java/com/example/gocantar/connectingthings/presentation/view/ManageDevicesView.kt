package com.example.gocantar.connectingthings.presentation.view

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.common.base.BaseActivityVM
import com.example.gocantar.connectingthings.presentation.view.adapter.ScannedDevicesRecyclerViewAdapter
import com.example.gocantar.connectingthings.presentation.viewmodel.ManageDevicesViewModel
import kotlinx.android.synthetic.main.activity_manage_devices.*

/**
 * Created by gocantar on 10/10/17.
 */
class ManageDevicesView : BaseActivityVM<ManageDevicesViewModel>() {

    override val mViewModelClass: Class<ManageDevicesViewModel> = ManageDevicesViewModel::class.java

    private val mAdapter: ScannedDevicesRecyclerViewAdapter by lazy {
        ScannedDevicesRecyclerViewAdapter(md_scanned_devices_recycler_view, mViewModel.mDevicesScannedList) {
            // Connect device
            Log.d(TAG, "Connecting to ${it.mac_address}")
            mViewModel.connectDevice(it)
        }
    }

    /**
     * Override functions
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_devices)

        md_back_button.setOnClickListener{ onBackPressed() }

        mViewModel.mRecyclerViewEvent.observe( this, Observer {
            it?.let {
                updateRecyclerView()
            } } )

        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.startScanDevices()
    }

    override fun onPause() {
        super.onPause()
        mViewModel.stopScanDevices()
    }


    /**
     * Private methods
     */

    private fun setUpRecyclerView(){
        md_scanned_devices_recycler_view.layoutManager = LinearLayoutManager(this)
        md_scanned_devices_recycler_view.adapter = mAdapter
    }

    private fun updateRecyclerView(){
        mAdapter.notifyDataSetChanged()
    }

    /**
     * Statics
     */

    companion object {

        val REQUEST_CODE = 10100

        fun getCallingIntent(context: Context) : Intent {
            return Intent(context, ManageDevicesView::class.java)
        }
    }



}