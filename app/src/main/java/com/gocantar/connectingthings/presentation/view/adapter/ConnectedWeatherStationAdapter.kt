package com.gocantar.connectingthings.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.presentation.extension.inflate
import com.gocantar.connectingthings.presentation.model.DeviceScannedView
import com.gocantar.connectingthings.presentation.view.adapter.holder.ConnectedWeatherStationHolder

/**
 * Created by gocantar on 29/1/18.
 */
class ConnectedWeatherStationAdapter(private val mRecyclerView: RecyclerView,
                                     private val mItems: List<DeviceScannedView>,
                                     private val mListener: (String) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = parent.inflate(R.layout.holder_weather_station_connected)
        return ConnectedWeatherStationHolder(view)
    }

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ConnectedWeatherStationHolder).bind(mItems[position].device, mListener)
    }
}