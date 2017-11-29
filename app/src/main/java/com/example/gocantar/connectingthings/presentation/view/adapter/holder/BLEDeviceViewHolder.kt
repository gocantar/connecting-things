package com.example.gocantar.connectingthings.presentation.view.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.common.ids.TypeID
import com.example.gocantar.connectingthings.presentation.model.DeviceScannedView
import kotlinx.android.synthetic.main.holder_ble_device.view.*
import org.jetbrains.anko.onClick

/**
 * Created by gocantar on 14/10/17.
 */
class BLEDeviceViewHolder(private val view: View):RecyclerView.ViewHolder(view) {

    fun bind(deviceScanned: DeviceScannedView, listener: (DeviceScannedView) -> Unit ) =
        with(view){
            // Set name
            hble_name.text = deviceScanned.name

            // Set mac address
            hble_address.text = deviceScanned.address

            // Set number of services
            if (deviceScanned.numberOfServices > 0){
                hble_services.visibility = View.VISIBLE
                hble_services.text = context.resources.getQuantityString(R.plurals.services, deviceScanned.numberOfServices, deviceScanned.numberOfServices)
            }else{
                hble_services.visibility = View.GONE
            }

            // Set RSSI
            val rssi = "${deviceScanned.rssi} dBm"
            hble_rssi.text =  rssi

            when(deviceScanned.typeID){
                TypeID.SENSOR, TypeID.PLUG, TypeID.BULB -> {
                    hble_connect_button.visibility = View.VISIBLE
                    hble_connect_button.onClick { listener(deviceScanned) }
                }
                else -> hble_connect_button.visibility = View.GONE

            }

        }



    
}