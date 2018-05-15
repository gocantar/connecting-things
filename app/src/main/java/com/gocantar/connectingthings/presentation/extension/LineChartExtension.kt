package com.gocantar.connectingthings.presentation.extension

import android.graphics.Color
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.gocantar.connectingthings.AppController
import com.gocantar.connectingthings.R
import org.jetbrains.anko.backgroundColor


/**
 * Created by gocantar on 8/2/18.
 */

fun LineChart.setDescription(descriptionMessage: String){
    val d = Description()
    d.isEnabled = true
    d.text = descriptionMessage
    description = d
}

fun LineChart.setUpPrimaryLineChart(){
    setWhiteBackground()
    enableTouchGestures()
    setUpLegend()
    setUpAxis()
}

fun LineChart.enableTouchGestures(){
    // enable touch gestures
    setTouchEnabled(true)
    // enable scaling and dragging
    isDragEnabled = true
    setScaleEnabled(true)
    setDrawGridBackground(false)
    // if disabled, scaling can be done on x- and y-axis separately
    setPinchZoom(true)
}

fun LineChart.setWhiteBackground(){
    // set color background
    backgroundColor = Color.WHITE
}

fun LineChart.setUpLegend(){
    legend.form = Legend.LegendForm.CIRCLE
    legend.textColor = resources.getColor(R.color.primaryText, AppController.instance.theme)
}

fun LineChart.setUpAxis(){
    xAxis.textColor = Color.BLACK
    xAxis.position = XAxis.XAxisPosition.BOTTOM
    xAxis.setDrawGridLines(false)
    xAxis.setDrawLabels(false)
    xAxis.setAvoidFirstLastClipping(true)
    xAxis.isEnabled = true

    axisLeft.textColor = Color.BLACK
    axisLeft.axisMaximum = 80f
    axisLeft.axisMinimum = 0f
    axisLeft.setDrawGridLines(false)

    axisRight.isEnabled = false



}

fun LineDataSet.setUpTemperatureStyle(){
    setDrawCircles(false)
    colors = ColorTemplate.PASTEL_COLORS.toList()
    color = Color.RED
}

fun LineDataSet.setUpHumidityStyle(){
    setDrawCircles(false)
    colors = ColorTemplate.PASTEL_COLORS.toList()
    color = Color.BLUE
}