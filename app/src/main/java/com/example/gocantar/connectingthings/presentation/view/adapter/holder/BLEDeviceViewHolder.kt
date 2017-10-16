package com.example.gocantar.connectingthings.presentation.view.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.gocantar.connectingthings.presentation.model.BLEDeviceView
import kotlinx.android.synthetic.main.holder_ble_device.view.*
import org.jetbrains.anko.onClick

/**
 * Created by gocantar on 14/10/17.
 */
class BLEDeviceViewHolder(private val view: View):RecyclerView.ViewHolder(view) {

    fun bind(device: BLEDeviceView, listener: (String) -> Unit ) =
        with(view){
            hble_name.text = device.name
            hble_address.text = device.mac_address
            hble_rssi.text = "${device.rssi} dBm"
            onClick { listener(device.mac_address) }
        }
    
}