package com.rsmi.softsheet

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pod.podspace.base.sheet.BottomSheetHelper

/**
 * Created by R.Sharifi
 * on 16 Sep 2020
 */
abstract class SheetView(context: Context, open var arguments: Bundle? = null) : ViewGroup(context),
    SheetHandler,
    LifecycleObserver,
    LifecycleOwner {

    companion object {
        const val DIALOG_STYLE = 0
        const val SNACK_BAR_STYLE = 1
    }

    protected var hideListener: Listener? = null

    var isDraggable = true

    var hideable = true

    open var showFab = false

    open var autoShow = true

    open var peekHeight = Resources.getSystem().displayMetrics.heightPixels

    open var style: Int = DIALOG_STYLE

    private var lifecycleRegistry: LifecycleRegistry? = null

    open lateinit var view: View

    private var helper: BottomSheetHelper? = null

    abstract fun getLayoutId(): Int

    abstract fun onStopSheet()


    init {

        view = LayoutInflater.from(context).inflate(getLayoutId(), null, false)

        addLifeCycleObserver()

        if (!isDraggable) {
            view.setOnClickListener {}
        }

    }


    private fun addLifeCycleObserver() {
        (context as AppCompatActivity).lifecycle.addObserver(this)
    }

    open fun onSheetAdded() {}

    open fun addHelper(helper: BottomSheetHelper) {
        this.helper = helper
    }

    open fun onFabClicked() {}


    open fun dismiss() {
        helper?.dismiss()
    }

    open fun show() {
        helper?.show()
    }

    open fun expand() {
        helper?.expand()
    }

    open fun hide() {
        helper?.hide()
    }

    open fun halfExpand() {
        helper?.halfExpand()
    }

    open fun collapse() {
        helper?.collapse()
    }

    open fun isShow(): Boolean {
        return helper?.isShow() ?: false
    }

    open fun isDismissed(): Boolean {
        return helper?.isDismissed() ?: false
    }

    open fun getString(id: Int): String {
        return context.getString(id)
    }

    fun getCallback(): BottomSheetBehavior.BottomSheetCallback? {
        return object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                onSheetStateChanged(bottomSheet, newState)
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                onSheetSlide(bottomSheet, slideOffset)
            }
        }
    }

    override fun onSheetStateChanged(bottomSheet: View, newState: Int) {
        when (newState) {

            BottomSheetBehavior.STATE_HIDDEN -> {
                hideListener?.onHide()
            }

        }
    }

    override fun onSheetSlide(bottomSheet: View, slideOffset: Float) {
    }

    override fun getPickHeight(): Int {
        return 0
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    private fun initializeLifecycleRegistry() {
        if (lifecycleRegistry == null) {
            lifecycleRegistry = LifecycleRegistry(this)
            lifecycleRegistry?.addObserver(this)
        }
    }

    override fun getLifecycle(): Lifecycle {
        initializeLifecycleRegistry()
        return lifecycleRegistry!!
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    fun setSheetListener(listener: Listener) {
        this.hideListener = listener
    }

    interface Listener {
        fun onHide()
    }
}