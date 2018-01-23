package com.example.gocantar.connectingthings.presentation.view.adapter.holder.render

import android.view.View
import android.view.ViewGroup

/**
 * Created by gocantar on 9/1/18.
 */
abstract class BaseRender {

    companion object {
        const val DEFAULT_VIEW = -2
    }

    var mParent: ViewGroup? = null
    var mType: Int = DEFAULT_VIEW

    fun viewType(type: Int): BaseRender{
        mType = type
        return this
    }

    fun parent(parent: ViewGroup): BaseRender{
        mParent = parent
        return this
    }

    abstract fun render(): View

}