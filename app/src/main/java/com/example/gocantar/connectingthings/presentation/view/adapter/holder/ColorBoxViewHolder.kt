package com.example.gocantar.connectingthings.presentation.view.adapter.holder

import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.gocantar.connectingthings.R
import kotlinx.android.synthetic.main.holder_bulb_color_box.view.*

/**
 * Created by gocantar on 16/11/17.
 */

class ColorBoxViewHolder( val view: View): RecyclerView.ViewHolder(view){
    fun bind(color: Int, listener: (Int) -> Unit){
        with(view){
           when{
                color != Color.WHITE -> hbcb_color.backgroundTintList =
                        ColorStateList.valueOf(color)
                else -> hbcb_color.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.grey, context.theme))
            }
            when(isClickable){
                true -> setOnClickListener { listener(color) }
            }
        }
    }
}
