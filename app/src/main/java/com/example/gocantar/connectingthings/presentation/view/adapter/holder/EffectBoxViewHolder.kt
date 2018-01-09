package com.example.gocantar.connectingthings.presentation.view.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.holder_effect_box.view.*

/**
 * Created by gocantar on 15/11/17.
 */
class EffectBoxViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    fun bind(text: String, listener: (String) -> Unit){
        with(view){
            heb_effect.text = text
            when{
                isClickable -> setOnClickListener{ listener(text) }
            }
        }
    }
}