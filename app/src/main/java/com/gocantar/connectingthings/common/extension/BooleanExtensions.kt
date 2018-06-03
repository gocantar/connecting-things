package com.gocantar.connectingthings.common.extension

import android.view.View

/**
 * Created by gocantar on 29/11/17.
 */

fun Boolean.toVisibleOrGone(): Int{
    return if (this)
        return View.VISIBLE
    else View.GONE
}

fun Boolean.toVisibleOrInvisible(): Int{
    return if (this)
        return View.VISIBLE
    else View.INVISIBLE
}