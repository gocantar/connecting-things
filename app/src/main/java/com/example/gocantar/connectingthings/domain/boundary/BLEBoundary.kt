package com.example.gocantar.connectingthings.domain.boundary

import android.content.Intent
import com.example.gocantar.connectingthings.domain.entity.BLEDevice
import io.reactivex.subjects.PublishSubject

/**
 * Created by gocantar on 13/10/17.
 */
interface BLEBoundary {

    val mPublisher: PublishSubject<BLEDevice>

    fun start()
    fun stop()
    fun isBLEnabled(): Boolean
    fun getRequestBLEIntent(): Intent
}