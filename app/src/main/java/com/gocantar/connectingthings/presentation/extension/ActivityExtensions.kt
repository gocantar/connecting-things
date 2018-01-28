package com.gocantar.connectingthings.presentation.extension

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import com.gocantar.connectingthings.R
import kotlinx.android.synthetic.main.loading_layout.view.*
import org.jetbrains.anko.find

/**
 * Created by gocantar on 21/11/17.
 */
fun Activity.showLoadingDialog(text: String = baseContext.resources.getString(R.string.loading)) {
    val layout: View = inflate(this, R.layout.loading_layout)
    layout.loading_text.text = text
}


fun Activity.removeLoadingDialog(){
    val rootLayout: FrameLayout = this.find(android.R.id.content)
    rootLayout.removeViewAt(rootLayout.childCount-1)
}

private fun inflate(activity: Activity, layout: Int): View = View.inflate(activity, layout, activity.find(android.R.id.content))