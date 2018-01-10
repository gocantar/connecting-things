package com.example.gocantar.connectingthings.presentation.view

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.presentation.Navigator
import com.example.gocantar.connectingthings.common.enum.Event
import com.example.gocantar.connectingthings.common.extension.toVisibility
import com.example.gocantar.connectingthings.presentation.view.adapter.ConnectedBulbsRecyclerViewAdapter
import com.example.gocantar.connectingthings.presentation.view.adapter.ConnectedPlugRecyclerViewAdapter
import com.example.gocantar.connectingthings.presentation.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*


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
            Log.d(TAG, "Opening bulb ${it.name} controller activity")
            Navigator.navigateToControlPlugView(this, it.address)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setEventsObserver()

        ma_manage_devices_icon.setOnClickListener {
            Navigator.navigateToManageDevicesActivity(this)
        }

        setUpRecyclersView()

    }

    override fun onStart() {
        super.onStart()
        mViewModel.enableBLE()
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

    private fun showBulbsRecyclerView(visible: Boolean){
        ma_bulbs_recycler_view.visibility = visible.toVisibility()
        ma_bulbs_no_device_connected_message.visibility = (!visible).toVisibility()
    }

    private fun showPlugsRecyclerView(visible: Boolean){
        ma_plugs_recycler_view.visibility = visible.toVisibility()
        ma_plugs_no_device_connected_message.visibility = (!visible).toVisibility()
    }

}
