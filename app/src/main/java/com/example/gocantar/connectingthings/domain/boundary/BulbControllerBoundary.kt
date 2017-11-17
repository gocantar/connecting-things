package com.example.gocantar.connectingthings.domain.boundary

import com.example.gocantar.connectingthings.domain.entity.BulbParams

/**
 * Created by gocantar on 6/11/17.
 */
interface BulbControllerBoundary {
    fun setColor(params: BulbParams)
}