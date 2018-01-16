package com.example.gocantar.connectingthings.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.presentation.extension.inflate
import com.example.gocantar.connectingthings.presentation.model.DeviceScannedView
import com.example.gocantar.connectingthings.presentation.view.adapter.holder.PlugConnectedViewHolder

/**
 * Created by gocantar on 29/11/17.
 */
class ConnectedPlugRecyclerViewAdapter(private val mRecyclerview: RecyclerView,
                                       private val mItems: List<DeviceScannedView>,
                                       private val mListener: (DeviceScannedView) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = parent.inflate(R.layout.holder_plug_connected)
        return PlugConnectedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as PlugConnectedViewHolder).bind(mItems[position], mListener)
    }

    override fun getItemCount(): Int = mItems.size

}