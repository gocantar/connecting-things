package com.gocantar.connectingthings.domain.usecase

import android.bluetooth.BluetoothGatt
import com.gocantar.connectingthings.domain.boundary.BulbControllerBoundary
import com.gocantar.connectingthings.domain.entity.BulbStatus
import com.gocantar.connectingthings.domain.entity.CharacteristicData
import com.gocantar.connectingthings.domain.interactor.DecodeBulbCharacteristicInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by gocantar on 8/1/18.
 */
class DecodeBulbCharacteristicActor @Inject constructor(private val mBulbController: BulbControllerBoundary ):
        DecodeBulbCharacteristicInteractor {

    private val mDisposable: CompositeDisposable = CompositeDisposable()

    override fun execute(disposable: DisposableObserver<BulbStatus>, gatt: BluetoothGatt, data: CharacteristicData) {
        val observable = mBulbController.decodeStatus(gatt, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        mDisposable.add(observable.subscribeWith(disposable))
    }

    override fun dispose() {
        when(!mDisposable.isDisposed){
            true -> mDisposable.dispose()
        }
    }
}