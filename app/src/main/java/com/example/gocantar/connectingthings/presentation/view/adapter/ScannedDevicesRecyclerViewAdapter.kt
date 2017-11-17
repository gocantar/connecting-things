package com.example.gocantar.connectingthings.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.presentation.base.extension.inflate
import com.example.gocantar.connectingthings.presentation.model.BLEDeviceView
import com.example.gocantar.connectingthings.presentation.view.adapter.holder.BLEDeviceViewHolder

/**
 * Created by gocantar on 14/10/17.
 */

class ScannedDevicesRecyclerViewAdapter(private val mRecyclerView: RecyclerView,
                                        private val mItems: Map<String, BLEDeviceView>,
                                        private val mListener: (BLEDeviceView) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = parent.inflate(R.layout.holder_ble_device)
        return BLEDeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as BLEDeviceViewHolder).bind(mItems.values.elementAt(position), mListener)
    }

    override fun getItemCount(): Int = mItems.size

}