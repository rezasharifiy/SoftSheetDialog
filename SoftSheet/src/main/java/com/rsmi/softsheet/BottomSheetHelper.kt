package com.pod.podspace.base.sheet

/**
 * Created by R.Sharifi
 * on Fri  Sep 2020
 */
interface BottomSheetHelper {
    fun dismiss()

    fun isDismissed() : Boolean

    fun show()

    fun hide()

    fun isShow():Boolean

    fun expand()

    fun halfExpand()

    fun collapse()

}