package com.example.gocantar.connectingthings.domain.interactor

import android.bluetooth.BluetoothGatt
import com.example.gocantar.connectingthings.domain.entity.BulbStatus
import com.example.gocantar.connectingthings.domain.entity.CharacteristicData
import io.reactivex.observers.DisposableObserver

/**
 * Created by gocantar on 8/1/18.
 */
interface DecodeBulbCharacteristicInteractor {
    fun execute(disposable: DisposableObserver<BulbStatus>, gatt: BluetoothGatt, data: CharacteristicData)
    fun dispose()
}