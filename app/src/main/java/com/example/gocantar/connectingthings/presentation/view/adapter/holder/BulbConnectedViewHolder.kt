package com.example.gocantar.connectingthings.presentation.view.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.gocantar.connectingthings.presentation.model.BulbConnectedView
import com.example.gocantar.connectingthings.presentation.model.DeviceScannedView
import kotlinx.android.synthetic.main.holder_bulb_connected.view.*
import org.jetbrains.anko.onClick


/**
 * Created by gocantar on 10/11/17.
 */
class BulbConnectedViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun bind(bulb: BulbConnectedView, listener: (BulbConnectedView) -> Unit){
        with(view){
            hbc_device_name.text = bulb.name
            view.onClick { listener(bulb) }
        }
    }

}