package com.gocantar.connectingthings.presentation.view.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.gocantar.connectingthings.presentation.model.BulbConnectedView
import kotlinx.android.synthetic.main.holder_bulb_connected.view.*
import org.jetbrains.anko.onClick


/**
 * Created by gocantar on 10/11/17.
 */
class BulbConnectedViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun bind(bulb: BulbConnectedView, lastItem: Boolean ,listener: (BulbConnectedView) -> Unit){
        with(view){
            hbc_device_name.text = bulb.name
            if (lastItem){
                line_divider.visibility = View.GONE
            }
            view.onClick { listener(bulb) }
        }
    }

}