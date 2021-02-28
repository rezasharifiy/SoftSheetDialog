package com.rsmi.softsheet

import android.view.View

/**
 * Created by R.Sharifi
 * on Fri  Sep 2020
 */
interface SheetHandler {

    fun onSheetStateChanged(bottomSheet : View , newState : Int)

    fun onSheetSlide(bottomSheet : View , slideOffset : Float)

    fun getPickHeight():Int

}