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


class ColorBoxViewRenderer {

    companion object {
        val DISABLE_VIEW = -1
        val UNSELECTED_VIEW = 0
        val SELECTED_VIEW = 1
    }

    private var mParent: ViewGroup? = null
    private var mType: Int = DISABLE_VIEW

    fun viewType(type: Int): ColorBoxViewRenderer{
        mType = type
        return this
    }


    fun parent(parent: ViewGroup): ColorBoxViewRenderer{
        mParent = parent
        return this
    }

    fun render(): View{
        val view = mParent!!.inflate(R.layout.holder_bulb_color_box)
        with(view){
            when(mType){
                DISABLE_VIEW -> hbcb_state.backgroundTintList = AppController.instance.getColorStateList(R.color.grey)
                UNSELECTED_VIEW -> hbcb_state.background = AppController.instance.getDrawable(R.drawable.oval_stroke)
                SELECTED_VIEW -> hbcb_state.backgroundTintList = AppController.instance.getColorStateList(R.color.colorPrimary)
            }
        }
        return view
    }

}