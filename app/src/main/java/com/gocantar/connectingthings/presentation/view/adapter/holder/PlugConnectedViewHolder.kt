package com.gocantar.connectingthings.presentation.view.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.gocantar.connectingthings.presentation.model.DeviceScannedView
import kotlinx.android.synthetic.main.holder_plug_connected.view.*
import org.jetbrains.anko.onClick

/**
 * Created by gocantar on 29/11/17.
 */


class PlugConnectedViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun bind(plug: DeviceScannedView, listener: (DeviceScannedView) -> Unit){
        with(view){
            hpc_device_name.text = plug.name
            view.onClick { listener(plug) }
        }
    }

}