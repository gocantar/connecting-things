package com.example.gocantar.connectingthings.presentation.view

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.presentation.Navigator
import com.example.gocantar.connectingthings.common.base.BaseActivity
import com.example.gocantar.connectingthings.data.BLEService
import com.example.gocantar.connectingthings.data.PermissionsService
import kotlinx.android.synthetic.main.activity_main.*


class MainActivityView : BaseActivity() {

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

        if (!BLEService.isBLEnabled()) {
            startActivityForResult(BLEService.getRequestBLEIntent(), BLEService.REQUEST_ENABLE_BT)
        } else{
            Log.d(TAG, "Bluetooth is enabled")
        }

        val requiredPermissions: Array<String> = PermissionsService.requiredPermissions()
        if (!requiredPermissions.isEmpty()) {
            Log.d(TAG, "There are required permission")
            ActivityCompat.requestPermissions(this, requiredPermissions, PermissionsService.PERMISSION_LOCATION_REQUEST_CODE)
        }else{
            Log.d(TAG, "There are not required permissions" )
        }
    }



}
