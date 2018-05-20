package com.gocantar.connectingthings.domain.usecase

import com.gocantar.connectingthings.domain.boundary.BLEServiceBoundary
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by gocantar on 20/5/18.
 */
class GetCharacteristicNotificationActor @Inject constructor(private val mBLEService: BLEServiceBoundary)
    : BaseInteractor<CharacteristicData, Unit>() {
    override fun buildUseCase(params: Unit): Observable<CharacteristicData> =
            mBLEService.mPublisherOfCharacteristic
}