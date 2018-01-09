package com.example.gocantar.connectingthings.presentation.view.adapter.holder.render

import android.view.View
import android.view.ViewGroup
import com.example.gocantar.connectingthings.AppController
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.presentation.extension.inflate
import kotlinx.android.synthetic.main.holder_bulb_color_box.view.*

/**
 * Created by gocantar on 16/11/17.
 */


class ColorBoxViewRenderer: BaseRender() {

    companion object {
        val DISABLE_VIEW = -1
        val AVAILABLE_VIEW = 0
        val SELECTED_VIEW = 1
    }

    override fun render(): View{
        val view = mParent!!.inflate(R.layout.holder_bulb_color_box)
        with(view){
            when(mType){
                AVAILABLE_VIEW -> {
                    hbcb_state.background = resources.getDrawable(R.drawable.ic_oval_stroke, context.theme)
                    isClickable = true
                }
                SELECTED_VIEW -> {
                    hbcb_state.backgroundTintList = resources.getColorStateList(R.color.colorPrimary, context.theme)
                    isClickable = false
                }
                else -> {
                    hbcb_state.backgroundTintList = resources.getColorStateList(R.color.grey, context.theme)
                    isClickable = false
                }
            }
        }
        return view
    }

}