package com.example.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import android.util.Log
import com.example.gocantar.connectingthings.domain.boundary.PlugControllerBoundary
import com.example.gocantar.connectingthings.domain.interactor.RequestLivePowerConsumptionInteractor
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by gocantar on 10/1/18.
 */
class RequestLivePowerConsumptionActor @Inject constructor( private val mPlugController: PlugControllerBoundary): RequestLivePowerConsumptionInteractor {

    private val INITIAL_DELAY: Long = 0
    private val PERIOD: Long = 10

    override fun execute(gatt: BluetoothGatt) {
        val executor = ScheduledThreadPoolExecutor(1)
        executor.scheduleAtFixedRate({
            Log.d("RequestLivePowerConsumption", "Doing request")
            mPlugController.requestPowerConsuption(gatt)
        }, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS)
    }
}