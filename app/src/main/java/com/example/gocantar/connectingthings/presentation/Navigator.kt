package com.example.gocantar.connectingthings.presentation

import android.content.Context
import com.example.gocantar.connectingthings.presentation.view.ManageDevicesView

/**
 * Created by gocantar on 10/10/17.
 */
class Navigator {
    companion object {
        fun navigateToManageDevicesActivity(context: Context){
            context.startActivity(ManageDevicesView.getCallingIntent(context))
        }
    }
}