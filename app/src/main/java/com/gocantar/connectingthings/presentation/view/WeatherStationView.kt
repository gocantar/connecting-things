package com.gocantar.connectingthings.presentation.view

import com.gocantar.connectingthings.presentation.viewmodel.WeatherStationViewModel

/**
 * Created by gocantar on 28/1/18.
 */
class WeatherStationView: BaseActivityVM<WeatherStationViewModel>() {

    override val mViewModelClass: Class<WeatherStationViewModel> =
            WeatherStationViewModel::class.java


}