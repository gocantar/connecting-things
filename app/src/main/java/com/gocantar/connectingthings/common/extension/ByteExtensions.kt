package com.gocantar.connectingthings.common.extension

/**
 * Created by gocantar on 6/1/18.
 */


fun Byte.toUnsignedInt() = this.toInt().and(0xFF)

fun ByteArray.fiveBytesToInt(): Int {
    return this[0].toUnsignedInt().shl(32) + this[1].toUnsignedInt().shl(24) +
            this[2].toUnsignedInt().shl(16) +  this[3].toUnsignedInt().shl(8) +
            this[4].toUnsignedInt()
}