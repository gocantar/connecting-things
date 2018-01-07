package com.example.gocantar.connectingthings.common.extension

/**
 * Created by gocantar on 6/1/18.
 */


fun Byte.toUnsignedInt() = this.toInt().and(0xFF)