package com.example.gocantar.connectingthings.presentation.view.adapter.holder

import android.content.res.ColorStateList
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.holder_bulb_color_box.view.*

/**
 * Created by gocantar on 16/11/17.
 */

class ColorBoxViewHolder( val view: View): RecyclerView.ViewHolder(view){

    fun bind(color: Int){
        with(view){
            hbcb_color.backgroundTintList = ColorStateList.valueOf(color)
        }
    }

}
