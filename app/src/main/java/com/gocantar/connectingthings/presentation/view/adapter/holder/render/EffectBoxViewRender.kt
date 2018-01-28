package com.gocantar.connectingthings.presentation.view.adapter.holder.render

import android.view.View
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.presentation.extension.inflate
import kotlinx.android.synthetic.main.holder_effect_box.view.*

/**
 * Created by gocantar on 9/1/18.
 */
class EffectBoxViewRender: BaseRender() {
    companion object {
        val DISABLE_VIEW = -1
        val AVAILABLE_VIEW = 0
        val SELECTED_VIEW = 1
    }

    override fun render(): View {
        val view = mParent!!.inflate(R.layout.holder_effect_box)
        with(view){
            when (mType) {
                SELECTED_VIEW -> {
                    heb_status_indicator.backgroundTintList =
                            resources.getColorStateList(R.color.colorPrimary, context.theme)
                    isClickable = false
                }

                DISABLE_VIEW -> {
                    heb_status_indicator.backgroundTintList =
                            resources.getColorStateList(R.color.grey, context.theme)
                    isClickable = false
                }
                else -> {
                    heb_status_indicator.background =
                            resources.getDrawable(R.drawable.ic_oval_stroke, context.theme)
                    isClickable = true
                }

            }
        }

        return view
    }

}