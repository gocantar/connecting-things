package com.gocantar.connectingthings.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gocantar.connectingthings.common.enum.State
import com.gocantar.connectingthings.presentation.model.BulbEffect
import com.gocantar.connectingthings.presentation.view.adapter.holder.EffectBoxViewHolder
import com.gocantar.connectingthings.presentation.view.adapter.holder.render.EffectBoxViewRender

/**
 * Created by gocantar on 15/11/17.
 */
class BulbEffectRecyclerViewAdapter(private val mRecyclerView: RecyclerView,
                                    private val mItems: List<BulbEffect>,
                                    private val mListener: (String) -> Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = EffectBoxViewRender().parent(parent)
                .viewType(viewType)
                .render()
        return EffectBoxViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as EffectBoxViewHolder).bind(mItems[position].effect, mListener)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(mItems[position].state){
            State.AVAILABLE -> EffectBoxViewRender.AVAILABLE_VIEW
            State.SELECTED -> EffectBoxViewRender.SELECTED_VIEW
            State.DISABLE -> EffectBoxViewRender.DISABLE_VIEW
        }
    }

}