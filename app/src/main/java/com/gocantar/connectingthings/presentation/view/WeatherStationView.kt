package com.gocantar.connectingthings.presentation.view

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.common.enum.State
import com.gocantar.connectingthings.common.ids.Key
import com.gocantar.connectingthings.presentation.extension.*
import com.gocantar.connectingthings.presentation.viewmodel.WeatherStationViewModel
import kotlinx.android.synthetic.main.activity_bulb_controller.*
import kotlinx.android.synthetic.main.activity_weather_station_controller.*

/**
 * Created by gocantar on 28/1/18.
 */
class WeatherStationView: BaseActivityVM<WeatherStationViewModel>() {


    private val mTemperatureLineData: LineData = LineData()
    private val mHumidityLineData: LineData = LineData()

    override val mViewModelClass: Class<WeatherStationViewModel> =
            WeatherStationViewModel::class.java

    private val onCheckedChangeListener: CompoundButton.OnCheckedChangeListener = CompoundButton.OnCheckedChangeListener{
        _, isChecked -> run {
        when (isChecked) {
            true -> mViewModel.enableNotifications()
            false -> mViewModel.disableNotifications()
        }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_station_controller)

        mViewModel.initialize(intent)
        setUpOnClickListeners()
        setUpObservers()

        awsc_temperature_chart.data = mTemperatureLineData
        awsc_temperature_chart.setUpPrimaryLineChart(80f)
        awsc_temperature_chart.setDescription("No data")

        awsc_humidity_chart.data = mHumidityLineData
        awsc_humidity_chart.setUpPrimaryLineChart(100f)
        awsc_humidity_chart.setDescription("No data")

        setUpNoDataChartView()

    }

    private fun setUpOnClickListeners(){
        awsc_back_button.setOnClickListener { onBackPressed() }
        awsc_reload_data_button.setOnClickListener { mViewModel.getSensorData() }
        awsc_publish_data_state.setOnCheckedChangeListener(onCheckedChangeListener)
    }

    private fun setUpObservers(){
        mViewModel.mTemperatureDataChange.observe(this, Observer {
            when(it) {
                true -> updateTemperatureChartView()
            }
        })
        mViewModel.mHumidityDataChange.observe(this, Observer {
            when(it) {
                true -> updateHumidityChartView()
            }
        })

        mViewModel.mNotificationsState.observe(this, Observer {
            when(it){
                State.AVAILABLE -> setOnSwitchState()
                else -> setOffSwitchState()
            }
        })

        mViewModel.mErrorSnackbar.observe(this, Observer {
            it?.let {
                showErrorSnackBar(it, awsc_container_layout)
            }
        })
    }

    private fun setOnSwitchState(){
        awsc_publish_data_state.setOnCheckedChangeListener(null)
        awsc_publish_data_state.isChecked = true
        awsc_publish_data_state.setOnCheckedChangeListener(onCheckedChangeListener)
    }

    private fun setOffSwitchState(){
        awsc_publish_data_state.setOnCheckedChangeListener(null)
        awsc_publish_data_state.isChecked = false
        awsc_publish_data_state.setOnCheckedChangeListener(onCheckedChangeListener)
    }

    private fun updateTemperatureChartView(){
        val lineDataSet = LineDataSet(mViewModel.mTemperatureData, "Temperature")
        lineDataSet.setUpTemperatureStyle()
        awsc_temperature_chart.setDescription("Datos de la temperatura sensada por Arduino 101")
        mTemperatureLineData.removeDataSet(0)
        mTemperatureLineData.addDataSet(lineDataSet)
        awsc_temperature_chart.notifyDataSetChanged()
        awsc_temperature_chart.invalidate()
    }

    private fun updateHumidityChartView(){
        val lineDataSet = LineDataSet(mViewModel.mHumidityData, "Humidity")
        lineDataSet.setUpHumidityStyle()
        awsc_humidity_chart.setDescription("Datos de la humedad sensada por Arduino 101")
        mHumidityLineData.removeDataSet(0)
        mHumidityLineData.addDataSet(lineDataSet)
        awsc_humidity_chart.notifyDataSetChanged()
        awsc_humidity_chart.invalidate()
    }

    private fun setUpNoDataChartView(){
        val lineDataSet = LineDataSet(listOf(Entry(0f, 20f), Entry(1f,40f)), "No Data")
        lineDataSet.setUpNoDataStyle()
        mTemperatureLineData.removeDataSet(0)
        mTemperatureLineData.addDataSet(lineDataSet)
        mHumidityLineData.removeDataSet(0)
        mHumidityLineData.addDataSet(lineDataSet)
        awsc_temperature_chart.notifyDataSetChanged()
        awsc_temperature_chart.invalidate()
        awsc_humidity_chart.notifyDataSetChanged()
        awsc_humidity_chart.invalidate()
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