package com.gocantar.connectingthings.presentation.view.adapter.holder

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import com.gocantar.connectingthings.R
import kotlinx.android.synthetic.main.holder_bulb_color_box.view.*
import org.jetbrains.anko.backgroundColor

/**
 * Created by gocantar on 16/11/17.
 */

class ColorBoxViewHolder( val view: View): RecyclerView.ViewHolder(view){
    fun bind(color: Int, listener: (Int) -> Unit){
        with(view){
            if (isClickable) {
                setOnClickListener { listener(color) }
                when{
                    color != Color.WHITE -> hbcb_color.backgroundColor = color
                    else -> hbcb_color.backgroundColor = resources.getColor(R.color.grey, context.theme)
                }
            }else{
                hbcb_color.backgroundColor = resources.getColor(R.color.grey, context.theme)
            }
        }
    }
}
