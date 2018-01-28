package com.gocantar.connectingthings.presentation.extension

import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar

/**
 * Created by gocantar on 11/1/18.
 */

fun ProgressBar.animate(value: Int){
    val animation: ObjectAnimator =
            ObjectAnimator.ofInt(this, "progress", progress, value )
    animation.duration = 2000
    animation.interpolator = DecelerateInterpolator()
    animation.start()
}