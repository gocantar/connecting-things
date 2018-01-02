package com.example.gocantar.connectingthings.presentation.view

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.common.enum.Event
import com.example.gocantar.connectingthings.presentation.extension.removeLoadingDialog
import com.example.gocantar.connectingthings.presentation.extension.showLoadingDialog
import com.example.gocantar.connectingthings.presentation.view.adapter.ConnectedDevicesRecyclerViewAdapter
import com.example.gocantar.connectingthings.presentation.view.adapter.ScannedDevicesRecyclerViewAdapter
import com.example.gocantar.connectingthings.presentation.viewmodel.ManageDevicesViewModel
import kotlinx.android.synthetic.main.activity_manage_devices.*

/**
 * Created by gocantar on 10/10/17.
 */
class ManageDevicesView : BaseActivityVM<ManageDevicesViewModel>() {

    override val mViewModelClass: Class<ManageDevicesViewModel> = ManageDevicesViewModel::class.java
    private val mScannedDevicesAdapter: ScannedDevicesRecyclerViewAdapter by lazy {
        ScannedDevicesRecyclerViewAdapter(md_scanned_devices_recycler_view, mViewModel.mDevicesScannedList) {
            mViewModel.connectDevice(it)
        }
    }
    private val mConnectedDevicesAdapter: ConnectedDevicesRecyclerViewAdapter by lazy {
        ConnectedDevicesRecyclerViewAdapter(md_connected_devices_recycler_view, mViewModel.mDevicesConnectedList){
            mViewModel.disconnectDevice(it)
        }
    }

    /**
     * Override functions
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_devices)

        md_back_button.setOnClickListener{ onBackPressed() }

        setUpObservers()
        setUpRecyclersView()
        mViewModel.initialize()
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

    private fun setUpObservers(){
        observeEvents()
        observeStates()
    }

    private fun observeEvents(){
        mViewModel.mRecyclerViewEvent.observe( this, Observer {
            it?.let {
                onModelViewEventReceived(it)
            } } )
    }

    private fun observeStates(){
        mViewModel.mConnectingDevice.observe(this, Observer {
            it?.let {
                when{
                    it -> showLoadingDialog(resources.getString(R.string.connecting))
                    else -> removeLoadingDialog()
                }
            }
        })

        mViewModel.mDisconnectingDevice.observe(this, Observer {
            it?.let {
                when{
                    it -> showLoadingDialog(resources.getString(R.string.disconnecting))
                    else -> removeLoadingDialog()
                }
            }
        })
    }

    private fun setUpRecyclersView(){
        md_scanned_devices_recycler_view.layoutManager = LinearLayoutManager(this)
        md_scanned_devices_recycler_view.adapter = mScannedDevicesAdapter

        md_connected_devices_recycler_view.layoutManager = LinearLayoutManager(this)
        md_connected_devices_recycler_view.adapter = mConnectedDevicesAdapter
    }

    private fun updateScannedRecyclerView(){
        mScannedDevicesAdapter.notifyDataSetChanged()
    }

    private fun updateConnectedRecyclerView(){
        mConnectedDevicesAdapter.notifyDataSetChanged()
    }

    private fun onModelViewEventReceived(event: Event){
        when(event){
            Event.LIST_CHANGED -> {
                updateScannedRecyclerView()
                updateConnectedRecyclerView()
            }
            else -> Log.d(TAG, "Other event was caught")
        }
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