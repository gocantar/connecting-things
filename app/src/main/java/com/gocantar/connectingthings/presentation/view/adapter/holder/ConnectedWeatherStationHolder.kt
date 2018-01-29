package com.gocantar.connectingthings.presentation.view.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.holder_weather_station_connected.view.*
import org.jetbrains.anko.onClick

/**
 * Created by gocantar on 29/1/18.
 */
class ConnectedWeatherStationHolder(private val view: View): RecyclerView.ViewHolder(view) {
    fun bind(text: String, listener: (String)-> Unit ){
        with(view){
            hwsc_address.text = text
            setOnClickListener { listener(text) }
        }
    }
}