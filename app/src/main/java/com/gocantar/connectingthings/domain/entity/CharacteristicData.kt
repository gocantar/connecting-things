package com.example.gocantar.connectingthings.domain.entity

import java.util.*

/**
 * Created by gocantar on 5/1/18.
 */
data class CharacteristicData(val address: String, val uuid: UUID, val value: ByteArray){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CharacteristicData

        if (address != other.address) return false
        if (uuid != other.uuid) return false
        if (!Arrays.equals(value, other.value)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = address.hashCode()
        result = 31 * result + uuid.hashCode()
        result = 31 * result + Arrays.hashCode(value)
        return result
    }

}