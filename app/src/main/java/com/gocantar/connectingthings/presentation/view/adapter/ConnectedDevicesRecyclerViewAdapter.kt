package com.example.gocantar.connectingthings.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.presentation.extension.inflate
import com.example.gocantar.connectingthings.presentation.model.DeviceScannedView
import com.example.gocantar.connectingthings.presentation.view.adapter.holder.ConnectedDeviceViewHolder

/**
 * Created by gocantar on 1/1/18.
 */
class ConnectedDevicesRecyclerViewAdapter(private val mRecyclerView: RecyclerView,
                                          private val mItems: Map<String, DeviceScannedView>,
                                          private val mListener: (String) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = parent.inflate(R.layout.holder_connected_device)
        return ConnectedDeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ConnectedDeviceViewHolder).bind(mItems.values.elementAt(position), mListener)
    }

    override fun getItemCount(): Int = mItems.size

}