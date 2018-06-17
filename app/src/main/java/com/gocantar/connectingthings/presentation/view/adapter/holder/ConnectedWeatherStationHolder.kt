package com.gocantar.connectingthings.presentation.view.adapter.holder

import android.bluetooth.BluetoothDevice
import android.support.v7.widget.RecyclerView
import android.view.View
import com.gocantar.connectingthings.device.sensor.WeatherStation
import kotlinx.android.synthetic.main.holder_weather_station_connected.view.*

/**
 * Created by gocantar on 29/1/18.
 */
class ConnectedWeatherStationHolder(private val view: View): RecyclerView.ViewHolder(view) {
    fun bind(device: BluetoothDevice,listener: (String)-> Unit ){
        with(view){
            hwsc_address.text = device.name
            setOnClickListener { listener(device.address) }
        }
    }
}