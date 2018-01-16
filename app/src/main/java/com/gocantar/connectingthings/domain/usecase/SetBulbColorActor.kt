package com.example.gocantar.connectingthings.domain.usecase

import com.example.gocantar.connectingthings.domain.boundary.BulbControllerBoundary
import com.example.gocantar.connectingthings.domain.entity.BulbParams
import com.example.gocantar.connectingthings.domain.interactor.SetColorInteractor
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by gocantar on 21/11/17.
 */
class SetBulbColorActor @Inject constructor(private val mBulbController: BulbControllerBoundary): SetColorInteractor {
    override fun execute(params: BulbParams) {
        doAsync {
            mBulbController.setColor(params)
        }
    }
}