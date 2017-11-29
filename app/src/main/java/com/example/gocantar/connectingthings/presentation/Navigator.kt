package com.example.gocantar.connectingthings.presentation

import android.app.Activity
import android.content.Context
import com.example.gocantar.connectingthings.presentation.view.ControlBulbView
import com.example.gocantar.connectingthings.presentation.view.ControlPlugView
import com.example.gocantar.connectingthings.presentation.view.ManageDevicesView

/**
 * Created by gocantar on 10/10/17.
 */
class Navigator {
    companion object {
        fun navigateToManageDevicesActivity(context: Context){
            (context as Activity).startActivityForResult(ManageDevicesView.getCallingIntent(context) , ManageDevicesView.REQUEST_CODE)
        }

        fun navigateToControlBulbView(context: Context, address: String){
            (context as Activity).startActivityForResult(ControlBulbView.getCallingIntent(context, address), ControlBulbView.REQUEST_CODE)
        }

        fun navigateToControlPlugView(context: Context, address: String){
            (context as Activity).startActivityForResult(ControlPlugView.getCallingIntent(context, address), ControlPlugView.REQUEST_CODE)
        }
    }

}