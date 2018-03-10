package com.gocantar.connectingthings.presentation.view

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.gocantar.connectingthings.common.ids.Key
import com.gocantar.connectingthings.presentation.view.adapter.BulbColorRecyclerViewAdapter
import com.gocantar.connectingthings.presentation.view.adapter.BulbEffectRecyclerViewAdapter
import com.gocantar.connectingthings.presentation.viewmodel.ControlBulbViewModel
import com.gocantar.connectingthings.R
import kotlinx.android.synthetic.main.activity_bulb_controller.*

/**
 * Created by gocantar on 14/11/17.
 */
class ControlBulbView : BaseActivityVM<ControlBulbViewModel>() {

    override val mViewModelClass: Class<ControlBulbViewModel> = ControlBulbViewModel::class.java

    private val mEffectAdapter: BulbEffectRecyclerViewAdapter by lazy {
        BulbEffectRecyclerViewAdapter(ba_effects_recycler_view, mViewModel.mEffectsList){
            Log.d(TAG, "Effect $it pressed")
            onEffectChanged(it)
        }
    }

    private val mColorsAdapter: BulbColorRecyclerViewAdapter by lazy {
        BulbColorRecyclerViewAdapter(ba_colors_recycler_view, mViewModel.mColorList){
            Log.d(TAG, "RED: ${Color.red(it)} - GREEN ${Color.green(it)} - BLUE ${Color.green(it)}")
            onColorChanged(it)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bulb_controller)

        ba_title.setOnClickListener{
            onBackPressed()
        }

        mViewModel.ba_title.observe(this, Observer { ba_title.text = it })


        mViewModel.initialize(intent.extras.getString(Key.DEVICE_ADDRESS))
        setUpRecyclersView()
        setUpEffectsRecyclerObserver()
        setUpColorsRecyclerObserver()

     }

    /**
     * Private functions
     */
    private fun setUpRecyclersView(){
        ba_effects_recycler_view.layoutManager = GridLayoutManager(this, 6)
        ba_effects_recycler_view.adapter = mEffectAdapter

        ba_colors_recycler_view.layoutManager = GridLayoutManager(this, 4)
        ba_colors_recycler_view.adapter = mColorsAdapter
    }

    private fun onColorChanged(color: Int){
        mViewModel.putColor(color)
    }

    private fun onEffectChanged(effect: Int){
        mViewModel.putEffect(effect)
    }

    private fun setUpEffectsRecyclerObserver(){
        mViewModel.mEffectsRecycler.observe(this, Observer {
            when(it){
                mViewModel.UPDATE_ALL_RECYCLER -> mEffectAdapter.notifyDataSetChanged()
                else -> {
                    it?.let {
                        mEffectAdapter.notifyItemChanged(it)
                    }
                }
            }
        })
    }

    private fun setUpColorsRecyclerObserver(){
        mViewModel.mColorsRecycler.observe(this, Observer { mColorsAdapter.notifyDataSetChanged() })
    }


    /**
     * Statics
     */
    companion object {

        const val REQUEST_CODE = 10200

        fun getCallingIntent(context: Context, address: String) : Intent {
            val intent = Intent(context, ControlBulbView::class.java)
            intent.putExtra(Key.DEVICE_ADDRESS, address)
            return intent
        }
    }

}