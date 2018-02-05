package com.gocantar.connectingthings.domain.interactor

/**
 * Created by gocantar on 5/2/18.
 */
interface SaveData<in T> {
    fun save(data: T)
}