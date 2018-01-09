package com.example.gocantar.connectingthings.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.gocantar.connectingthings.common.enum.State
import com.example.gocantar.connectingthings.presentation.model.BulbColor
import com.example.gocantar.connectingthings.presentation.view.adapter.holder.ColorBoxViewHolder
import com.example.gocantar.connectingthings.presentation.view.adapter.holder.render.ColorBoxViewRenderer

/**
 * Created by gocantar on 16/11/17.
 */
class BulbColorRecyclerViewAdapter(private val mRecyclerView: RecyclerView,
                                   private val mItems: List<BulbColor>,
                                   private val mListener: (Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val item = mItems[position]
        (holder as ColorBoxViewHolder).bind(item.color, mListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ColorBoxViewRenderer().parent(parent)
                .viewType(viewType)
                .render()
        return ColorBoxViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(mItems[position].state){
            State.DISABLE -> ColorBoxViewRenderer.DISABLE_VIEW
            State.SELECTED -> ColorBoxViewRenderer.SELECTED_VIEW
            State.AVAILABLE -> ColorBoxViewRenderer.AVAILABLE_VIEW
        }
    }


}