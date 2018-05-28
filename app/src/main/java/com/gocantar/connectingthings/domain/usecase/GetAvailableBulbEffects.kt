package com.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.domain.boundary.BulbControllerBoundary
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by gocantar on 28/5/18.
 */
class GetAvailableBulbEffects @Inject constructor(private val bulbController: BulbControllerBoundary) : BaseInteractor<Int, BluetoothGatt>() {
    override fun buildUseCase(params: BluetoothGatt): Observable<Int> {
        return bulbController.getAvailableEffects(params)
    }
}