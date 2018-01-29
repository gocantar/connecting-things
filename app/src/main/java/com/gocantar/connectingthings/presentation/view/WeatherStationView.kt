package com.gocantar.connectingthings.presentation.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.common.ids.Key
import com.gocantar.connectingthings.presentation.viewmodel.WeatherStationViewModel

/**
 * Created by gocantar on 28/1/18.
 */
class WeatherStationView: BaseActivityVM<WeatherStationViewModel>() {

    override val mViewModelClass: Class<WeatherStationViewModel> =
            WeatherStationViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_station_controller)
    }



    companion object {

        const val REQUEST_CODE = 10500

        fun getCallingIntent(context: Context, address: String): Intent{
            val intent = Intent(context, WeatherStationView::class.java)
            intent.putExtra(Key.DEVICE_ADDRESS, address )
            return intent
        }

    }
}