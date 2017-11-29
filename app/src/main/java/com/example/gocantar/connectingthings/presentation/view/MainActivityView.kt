package com.example.gocantar.connectingthings.presentation.view

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.presentation.Navigator
import com.example.gocantar.connectingthings.common.enum.Event
import com.example.gocantar.connectingthings.common.extension.toVisibility
import com.example.gocantar.connectingthings.data.controller.BLEController
import com.example.gocantar.connectingthings.data.PermissionsService
import com.example.gocantar.connectingthings.presentation.view.adapter.ConnectedBulbsRecyclerViewAdapter
import com.example.gocantar.connectingthings.presentation.view.adapter.ConnectedPlugRecyclerViewAdapter
import com.example.gocantar.connectingthings.presentation.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_manage_device_button.*


class MainActivityView : BaseActivityVM<MainActivityViewModel>() {

    override val mViewModelClass: Class<MainActivityViewModel> = MainActivityViewModel::class.java

    private val mBulbsAdapter: ConnectedBulbsRecyclerViewAdapter by lazy {
        ConnectedBulbsRecyclerViewAdapter(ma_bulbs_recycler_view, mViewModel.mBulbsConnected){
            Log.d(TAG, "Opening bulb ${it.name} controller activity")
            Navigator.navigateToControlBulbView(this, it.address)
        }
    }

    private val mPlugsAdapter: ConnectedPlugRecyclerViewAdapter by lazy {
        ConnectedPlugRecyclerViewAdapter(ma_plugs_recycler_view, mViewModel.mPlugsConnected){
            Log.d(TAG, "Selected plug ${it.name}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setEventsObserver()

        ma_manage_devices_layout.setOnClickListener {
            Navigator.navigateToManageDevicesActivity(this)
        }

        setUpRecyclersView()
    }

    override fun onStart() {
        super.onStart()
        checkBLE()
        checkPermissions()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            ManageDevicesView.REQUEST_CODE -> {
                // Get devices connected and update the panel
                mViewModel.updateDevicesConnected()
            }
        }
    }


    /**
     * Private functions
     */

    private fun setUpRecyclersView(){
        // Setup bulbs adapter
        showBulbsRecyclerView(mViewModel.getBulbsRecyclerViewVisibility())
        ma_bulbs_recycler_view.layoutManager = GridLayoutManager(this, 3)
        ma_bulbs_recycler_view.adapter = mBulbsAdapter

        // Setup plugs adapter
        showPlugsRecyclerView(mViewModel.getPlugsRecyclerViewVisibility())
        ma_plugs_recycler_view.layoutManager = GridLayoutManager(this, 3)
        ma_plugs_recycler_view.adapter = mPlugsAdapter
    }

    private fun setEventsObserver(){
        mViewModel.mRecyclerViewEvent.observe(this, Observer {
            it.let {
                when(it){
                    Event.BULB_LIST_CHANGED -> updateBulbsRecyclerView()
                    Event.PLUG_LIST_CHANGED -> updatePlugsRecyclerView()
                    else -> Log.d(TAG, "The event could not be captured")
                }
            }
        })
    }

    private fun updateBulbsRecyclerView(){
        showBulbsRecyclerView(mViewModel.getBulbsRecyclerViewVisibility())
        mBulbsAdapter.notifyDataSetChanged()
    }

    private fun updatePlugsRecyclerView(){
        showPlugsRecyclerView(mViewModel.getPlugsRecyclerViewVisibility())
        mPlugsAdapter.notifyDataSetChanged()
    }

    private fun checkBLE(){
        if (!mViewModel.isBLEEnabled()) {
            startActivityForResult(mViewModel.getRequestBLEIntent(), BLEController.REQUEST_CODE)
        } else{
            Log.d(TAG, "Bluetooth is enabled")
        }
    }

    private fun checkPermissions(){
        val requiredPermissions: Array<String> = PermissionsService.requiredPermissions()
        if (!requiredPermissions.isEmpty()) {
            Log.d(TAG, "There are required permission")
            ActivityCompat.requestPermissions(this, requiredPermissions, PermissionsService.REQUEST_CODE)
        }else{
            Log.d(TAG, "There are not required permissions" )
        }
    }

    private fun showBulbsRecyclerView(visibility: Boolean){
        ma_bulbs_recycler_view.visibility = visibility.toVisibility()
        ma_bulbs_no_device_connected_message.visibility = (!visibility).toVisibility()
    }

    private fun showPlugsRecyclerView(visibility: Boolean){
        ma_plugs_recycler_view.visibility = visibility.toVisibility()
        ma_plugs_no_device_connected_message.visibility = (!visibility).toVisibility()
    }


}
