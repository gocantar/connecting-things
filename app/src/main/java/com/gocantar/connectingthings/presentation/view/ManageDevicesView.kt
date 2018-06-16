package com.gocantar.connectingthings.presentation.view

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.common.enum.Event
import com.gocantar.connectingthings.common.extension.toVisibleOrInvisible
import com.gocantar.connectingthings.presentation.extension.removeLoadingDialog
import com.gocantar.connectingthings.presentation.extension.showLoadingDialog
import com.gocantar.connectingthings.presentation.view.adapter.ConnectedDevicesRecyclerViewAdapter
import com.gocantar.connectingthings.presentation.view.adapter.ScannedDevicesRecyclerViewAdapter
import com.gocantar.connectingthings.presentation.viewmodel.ManageDevicesViewModel
import kotlinx.android.synthetic.main.activity_bulb_controller.*
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
        observeRecyclerViewVisibility()
    }

    private fun observeEvents(){
        mViewModel.mRecyclerViewEvent.observe( this, Observer {
            it?.let {
                onModelViewEventReceived(it)
            } } )

        mViewModel.mErrorSnackbar.observe(this, Observer {
            it?.let {
                showErrorSnackBar(it, md_container_layout)
            }
        })
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

    private fun observeRecyclerViewVisibility(){
        mViewModel.mShowConnectedDevices.observe(this, Observer {
            it?.let {
                md_connected_devices_recycler_view.visibility = it.toVisibleOrInvisible()
                md_no_devices_connected.visibility = it.not().toVisibleOrInvisible()
            }
        })
        mViewModel.mShowScannedDevices.observe(this, Observer {
            it?.let {
                md_scanned_devices_recycler_view.visibility = it.toVisibleOrInvisible()
                md_no_devices_scanned.visibility = it.not().toVisibleOrInvisible()
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

        const val REQUEST_CODE = 10100

        fun getCallingIntent(context: Context) : Intent {
            return Intent(context, ManageDevicesView::class.java)
        }
    }



}