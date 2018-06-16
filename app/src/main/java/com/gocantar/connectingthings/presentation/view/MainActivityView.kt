package com.gocantar.connectingthings.presentation.view

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.common.enum.Event
import com.gocantar.connectingthings.common.extension.toVisibleOrGone
import com.gocantar.connectingthings.presentation.Navigator
import com.gocantar.connectingthings.presentation.view.adapter.ConnectedBulbsRecyclerViewAdapter
import com.gocantar.connectingthings.presentation.view.adapter.ConnectedPlugRecyclerViewAdapter
import com.gocantar.connectingthings.presentation.view.adapter.ConnectedWeatherStationAdapter
import com.gocantar.connectingthings.presentation.viewmodel.MainActivityViewModel
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

    private val mWeatherStationAdapter: ConnectedWeatherStationAdapter by lazy {
        ConnectedWeatherStationAdapter(ma_ws_recycler_view, mViewModel.mSensorsConnected){
            Log.d(TAG, "Opening sensor $it controller activity")
            Navigator.navigateToWeatherStationView(this, it)
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

        mViewModel.initialize()
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
        setUpBulbsAdapter()
        setUpPlugsAdapter()
        setUpSensorsAdapter()
    }

    private fun setUpBulbsAdapter(){
        // Setup bulbs adapter
        showBulbsRecyclerView(mViewModel.getBulbsRecyclerViewVisibility())
        ma_bulbs_recycler_view.layoutManager = GridLayoutManager(this, 3)
        ma_bulbs_recycler_view.adapter = mBulbsAdapter
    }

    private fun setUpPlugsAdapter(){
        // Setup plugs adapter
        showPlugsRecyclerView(mViewModel.getPlugsRecyclerViewVisibility())
        ma_plugs_recycler_view.layoutManager = GridLayoutManager(this, 3)
        ma_plugs_recycler_view.adapter = mPlugsAdapter
    }

    private fun setUpSensorsAdapter(){
        // Setup sensors adapter
        showSensorsRecyclerView(mViewModel.getSensorsRecyclerViewVisibility())
        ma_ws_recycler_view.layoutManager = GridLayoutManager(this, 3)
        ma_ws_recycler_view.adapter = mWeatherStationAdapter
    }

    private fun setEventsObserver(){
        mViewModel.mRecyclerViewEvent.observe(this, Observer {
            it.let {
                when(it){
                    Event.BULB_LIST_CHANGED -> updateBulbsRecyclerView()
                    Event.PLUG_LIST_CHANGED -> updatePlugsRecyclerView()
                    Event.SENSOR_LIST_CHANGED -> updateSensorRecyclerView()
                    else -> Log.d(TAG, "The event could not be captured")
                }
            }
        })
        mViewModel.mErrorSnackbar.observe(this, Observer {
            it?.let {
                showErrorSnackBar(it, ma_container_layout)
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

    private fun updateSensorRecyclerView(){
        showSensorsRecyclerView(mViewModel.getSensorsRecyclerViewVisibility())
        mWeatherStationAdapter.notifyDataSetChanged()
    }

    private fun showBulbsRecyclerView(visible: Boolean){
        ma_bulbs_recycler_view.visibility = visible.toVisibleOrGone()
        ma_bulbs_no_device_connected_message.visibility = (!visible).toVisibleOrGone()
    }

    private fun showPlugsRecyclerView(visible: Boolean){
        ma_plugs_recycler_view.visibility = visible.toVisibleOrGone()
        ma_plugs_no_device_connected_message.visibility = (!visible).toVisibleOrGone()
    }

    private fun showSensorsRecyclerView(visible: Boolean){
        ma_ws_recycler_view.visibility = visible.toVisibleOrGone()
        ma_ws_no_device_connected_message.visibility = (!visible).toVisibleOrGone()
    }

}
