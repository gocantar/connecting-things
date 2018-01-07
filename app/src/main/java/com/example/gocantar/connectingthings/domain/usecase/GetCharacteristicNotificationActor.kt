package com.example.gocantar.connectingthings.domain.usecase

import com.example.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.example.gocantar.connectingthings.domain.entity.CharacteristicData
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by gocantar on 5/1/18.
 */
class GetCharacteristicNotificationActor @Inject constructor(private val mBLEService: BLEServiceBoundary)
            : BaseInteractor<CharacteristicData, Unit>() {
    override fun buildUseCase(params: Unit): Observable<CharacteristicData>  =
        mBLEService.mPublisherOfCharacteristic
}