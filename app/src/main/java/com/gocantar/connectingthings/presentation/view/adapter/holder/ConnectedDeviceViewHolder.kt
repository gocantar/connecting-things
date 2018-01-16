package com.example.gocantar.connectingthings.presentation.view.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.presentation.model.DeviceScannedView
import kotlinx.android.synthetic.main.holder_connected_device.view.*
import org.jetbrains.anko.onClick

/**
 * Created by gocantar on 1/1/18.
 */
class ConnectedDeviceViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun bind(deviceScanned: DeviceScannedView, listener: (String) -> Unit ) =
            with(view){
                // Set name
                hcd_name.text = deviceScanned.name
                // Set mac address
                hcd_address.text = deviceScanned.address
                hcd_services.text = context.resources.getQuantityString(R.plurals.services, deviceScanned.numberOfServices, deviceScanned.numberOfServices)
                hcd_disconnect_button.onClick { listener(deviceScanned.address) }
            }

}