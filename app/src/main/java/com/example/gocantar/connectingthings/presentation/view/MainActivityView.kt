package com.example.gocantar.connectingthings.presentation.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.presentation.Navigator
import com.example.gocantar.connectingthings.common.base.BaseActivityVM
import com.example.gocantar.connectingthings.data.controller.BLEController
import com.example.gocantar.connectingthings.data.PermissionsService
import com.example.gocantar.connectingthings.presentation.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivityView : BaseActivityVM<MainActivityViewModel>() {

    override val mViewModelClass: Class<MainActivityViewModel> = MainActivityViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * onClick Listeners
         */
        ma_manage_devices_layout.setOnClickListener {
            Navigator.navigateToManageDevicesActivity(this)
        }
        
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


}
