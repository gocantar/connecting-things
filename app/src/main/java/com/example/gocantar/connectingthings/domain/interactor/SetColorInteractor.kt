package com.example.gocantar.connectingthings.domain.interactor

import com.example.gocantar.connectingthings.domain.entity.BulbParams

/**
 * Created by gocantar on 21/11/17.
 */
interface SetColorInteractor {
    fun execute(params: BulbParams)
}