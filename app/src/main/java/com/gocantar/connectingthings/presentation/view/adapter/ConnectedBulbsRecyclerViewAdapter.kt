package com.gocantar.connectingthings.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gocantar.connectingthings.R
import com.gocantar.connectingthings.presentation.extension.inflate
import com.gocantar.connectingthings.presentation.model.BulbConnectedView
import com.gocantar.connectingthings.presentation.view.adapter.holder.BulbConnectedViewHolder

/**
 * Created by gocantar on 10/11/17.
 */


class ConnectedBulbsRecyclerViewAdapter(private val mRecyclerView: RecyclerView,
                                        private val mItems: List<BulbConnectedView>,
                                        private val mListener: (BulbConnectedView) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = parent.inflate(R.layout.holder_bulb_connected)
        return BulbConnectedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as BulbConnectedViewHolder).bind(mItems[position], mListener)
    }

    override fun getItemCount(): Int = mItems.size

}