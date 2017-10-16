package com.example.gocantar.connectingthings.presentation.view

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.common.base.BaseActivityVM
import com.example.gocantar.connectingthings.common.enum.Event
import com.example.gocantar.connectingthings.presentation.view.adapter.ScannedDevicesAdapter
import com.example.gocantar.connectingthings.presentation.viewmodel.ManageDevicesViewModel
import kotlinx.android.synthetic.main.activity_manage_devices.*

/**
 * Created by gocantar on 10/10/17.
 */
class ManageDevicesView : BaseActivityVM<ManageDevicesViewModel>() {

    override val mViewModelClass: Class<ManageDevicesViewModel> = ManageDevicesViewModel::class.java

    private val mAdapter: ScannedDevicesAdapter by lazy {
        ScannedDevicesAdapter(md_scanned_devices_recycler_view, mViewModel.mDevicesScannedList,
                mListener = { Log.d(TAG, "Connect to $it") })
    }

    /**
     * Override functions
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_devices)

        md_back_button.setOnClickListener{ onBackPressed() }

        mViewModel.mRecyclerViewEvent.observe( this, Observer { it?.let {
            when (it) {
                Event.LIST_CHANGED -> updateRecyclerView()
            }
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
     * Class methods
     */

    private fun setUpRecyclerView(){
        md_scanned_devices_recycler_view.layoutManager = LinearLayoutManager(this)
        md_scanned_devices_recycler_view.adapter = mAdapter
    }

    private fun updateRecyclerView(){
        mAdapter.notifyDataSetChanged()
    }

    /**
     * Statics methods
     */

    companion object {
        fun getCallingIntent(context: Context) : Intent {
            return Intent(context, ManageDevicesView::class.java)
        }
    }



}