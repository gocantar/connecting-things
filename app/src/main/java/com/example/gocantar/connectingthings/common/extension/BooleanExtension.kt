package com.example.gocantar.connectingthings.common.extension

import android.view.View

/**
 * Created by gocantar on 29/11/17.
 */

fun Boolean.toVisibility(): Int{
    return if (this)
        return View.VISIBLE
    else View.GONE
}