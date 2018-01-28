package com.gocantar.connectingthings.domain.interactor

import com.gocantar.connectingthings.domain.entity.BulbParams

/**
 * Created by gocantar on 21/11/17.
 */
interface SetColorInteractor {
    fun execute(params: BulbParams)
}