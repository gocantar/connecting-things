package com.example.gocantar.connectingthings.base
import android.support.v7.widget.RecyclerView

/**
 * Created by gocantar on 13/7/17.
 */
abstract class BaseAdapter<T> (val mRecyclerView: RecyclerView, val mItems: MutableList<T>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val VIEW_ITEM = 1

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemViewType(position: Int): Int{
        return VIEW_ITEM
    }

}