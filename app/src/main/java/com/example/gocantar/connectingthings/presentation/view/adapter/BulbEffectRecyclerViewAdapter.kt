package com.example.gocantar.connectingthings.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.gocantar.connectingthings.R
import com.example.gocantar.connectingthings.presentation.base.extension.inflate
import com.example.gocantar.connectingthings.presentation.model.BulbEffect
import com.example.gocantar.connectingthings.presentation.view.adapter.holder.EffectBoxViewHolder

/**
 * Created by gocantar on 15/11/17.
 */
class BulbEffectRecyclerViewAdapter(private val mRecyclerView: RecyclerView,
                                    private val mItems: List<BulbEffect>,
                                    private val mListener: (String) -> Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = parent.inflate(R.layout.holder_effect_box)
        return EffectBoxViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as EffectBoxViewHolder).bind(mItems[position].effect, mListener)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

}