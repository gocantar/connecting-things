package com.gocantar.connectingthings.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.presentation.extension.inflate
import com.gocantar.connectingthings.presentation.model.DeviceScannedView
import com.gocantar.connectingthings.presentation.view.adapter.holder.ScannedDeviceViewHolder

/**
 * Created by gocantar on 14/10/17.
 */

class ScannedDevicesRecyclerViewAdapter(private val mRecyclerView: RecyclerView,
                                        private val mItems: Map<String, DeviceScannedView>,
                                        private val mListener: (DeviceScannedView) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = parent.inflate(R.layout.holder_scanned_device)
        return ScannedDeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ScannedDeviceViewHolder).bind(mItems.values.elementAt(position), mListener)
    }

    override fun getItemCount(): Int = mItems.size

}