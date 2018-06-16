package com.gocantar.connectingthings.presentation

import android.app.Activity
import android.content.Context
import com.gocantar.connectingthings.presentation.view.ControlBulbView
import com.gocantar.connectingthings.presentation.view.ControlPlugView
import com.gocantar.connectingthings.presentation.view.ManageDevicesView
import com.gocantar.connectingthings.presentation.view.WeatherStationView

/**
 * Created by gocantar on 10/10/17.
 */
class Navigator {
    companion object {
        fun navigateToManageDevicesActivity(context: Context){
            context as Activity
            context.startActivityForResult(ManageDevicesView.getCallingIntent(context) , ManageDevicesView.REQUEST_CODE)
        }

        fun navigateToControlBulbView(context: Context, address: String){
            context as Activity
            context.startActivityForResult(ControlBulbView.getCallingIntent(context, address), ControlBulbView.REQUEST_CODE)
        }

        fun navigateToControlPlugView(context: Context, address: String){
            (context as Activity).startActivityForResult(ControlPlugView.getCallingIntent(context, address), ControlPlugView.REQUEST_CODE)
        }

        fun navigateToWeatherStationView(context: Context, address: String){
            (context as Activity).startActivityForResult(WeatherStationView.getCallingIntent(context, address), WeatherStationView.REQUEST_CODE)
        }
    }

}