package com.example.gocantar.connectingthings.presentation.extension



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by gocantar on 14/10/17.
 */

fun ViewGroup.inflate(layoutRes: Int): View{
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}
