package com.example.gocantar.connectingthings.presentation.view.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.common.ids.TypeID
import com.example.gocantar.connectingthings.presentation.model.DeviceScannedView
import kotlinx.android.synthetic.main.holder_scanned_device.view.*
import org.jetbrains.anko.onClick

/**
 * Created by gocantar on 14/10/17.
 */
class ScannedDeviceViewHolder(private val view: View):RecyclerView.ViewHolder(view) {

    fun bind(deviceScanned: DeviceScannedView, listener: (DeviceScannedView) -> Unit ) =
        with(view){
            // Set name
            hsd_name.text = deviceScanned.name

            // Set mac address
            hsd_address.text = deviceScanned.address

            // Set number of services
            if (deviceScanned.numberOfServices > 0){
                hsd_services.text = context.resources.getQuantityString(R.plurals.services, deviceScanned.numberOfServices, deviceScanned.numberOfServices)
            }else{
                hsd_services.text = context.resources.getString(R.string.no_available_services)
            }

            // Set RSSI
            val rssi = "${deviceScanned.rssi} dBm"
            hsd_rssi.text =  rssi

            when(deviceScanned.typeID){
                TypeID.SENSOR, TypeID.PLUG, TypeID.BULB -> {
                    hsd_connect_button.visibility = View.VISIBLE
                    hsd_connect_button.onClick { listener(deviceScanned) }
                }
                else -> hsd_connect_button.visibility = View.GONE

            }

        }



    
}