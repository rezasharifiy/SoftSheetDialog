package com.rsmi.softsheet

import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pod.podspace.base.sheet.BottomSheetHelper
import com.rsmi.softsheet.util.Utils

/**
 * Created by R.Sharifi
 * on 16 Sep 2020
 */
class BottomSheetContainer(private val view: CoordinatorLayout, val fab: View?) : BottomSheetHelper,
    View.OnClickListener {

    private var isExpanded = false
    private var backgroundView: View? = null
    private var isFullHeight = false
    private lateinit var behavior: BottomSheetBehavior<ViewGroup>
    private var customView: SheetView? = null
    private var containerView: ViewGroup? = null


    companion object {
        const val CONTAINER_TAG = "CONTAINER_TAG"
        const val BACKGROUND_TAG = "BACKGROUND_TAG"
        val TAG: String = BottomSheetContainer::class.java.name
    }


    private fun createContainerView(): ViewGroup {
        containerView = FrameLayout(view.context)

        val param = if (isFullHeight) {
            CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.MATCH_PARENT,
                CoordinatorLayout.LayoutParams.MATCH_PARENT
            )
        } else {
            CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.MATCH_PARENT,
                CoordinatorLayout.LayoutParams.WRAP_CONTENT
            )
        }

        behavior = BottomSheetBehavior<ViewGroup>()

        behavior.isHideable = customView!!.hideable
        behavior.peekHeight = customView!!.peekHeight
        behavior.isDraggable = customView!!.isDraggable

        param.behavior = behavior
        containerView!!.layoutParams = param

        containerView!!.tag = CONTAINER_TAG

        return containerView!!
    }

    private fun addShadow() {
        val bView = getBackgroundView()
        if (bView != null)
            view.addView(
                bView
            )
    }


    fun showView(customView: SheetView, fullHeight: Boolean? = false) {

        this.customView = null

        this.customView = customView

        if (customView.isShow())
            return

        removeShadow()

        if (customView.showFab) {
            fab?.visibility = VISIBLE
            fab?.setOnClickListener(this)
        } else {
            fab?.visibility = GONE
        }

        customView.view.isClickable = true

        isFullHeight = fullHeight!!

        customView.addHelper(this)

        getContainerView().removeAllViews()

        getContainerView().addView(customView.view)


        val callBack = customView.getCallback()

        if (callBack != null)
            behavior.addBottomSheetCallback(callBack)

        listenChange()

        if (customView.autoShow) {
            showBottomSheet()
        } else {
            customView.onSheetAdded()
        }
    }


    private fun removeShadow() {
        val bView = getBackgroundView()
        if (bView != null)
            view.removeView(bView)
    }

    private fun getBackgroundView(): View? {

        if (containerView == null)
            return null

        if (backgroundView == null) {
            backgroundView = View(view.context)
            val params =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            backgroundView!!.layoutParams = params
            backgroundView!!.isEnabled = true
            backgroundView!!.isClickable = true
            backgroundView!!.tag = BACKGROUND_TAG
            backgroundView!!.visibility = View.GONE
            backgroundView!!.setBackgroundColor(containerView!!.context.resources.getColor(R.color.dark_transparent))
            backgroundView!!.setOnClickListener {
                hideBottomSheet()
            }

        }

        return backgroundView
    }

    private fun getContainerView(): ViewGroup {

        if (containerView == null) {

            view.addView(createContainerView())

            if (customView!!.style == SheetView.DIALOG_STYLE) {
                addShadow()
            } else {
                backgroundView = null
            }

        }


        return containerView!!
    }


    private fun enableDisableViewGroup(viewGroup: ViewGroup, enabled: Boolean) {
        val childCount = viewGroup.childCount
        for (i in 0 until childCount) {
            val view = viewGroup.getChildAt(i)
            view.isEnabled = enabled
            if (view.tag == CONTAINER_TAG) {
                view.isEnabled = false
            } else {

                if (view is ViewGroup) {
                    if (enabled) {
                        enableDisableViewGroup(view, true)
                    } else if (!isIgnoreView(view)) {
                        enableDisableViewGroup(view, false)
                    }
                }
            }
        }
    }

    private fun isIgnoreView(view: ViewGroup) =
        view.tag == CONTAINER_TAG || view.tag == BACKGROUND_TAG

    private fun showBottomSheet() {
        if (behavior == null)
            throw NullPointerException()

        //        if (behavior?.state != BottomSheetBehavior.STATE_EXPANDED)
        behavior?.state = BottomSheetBehavior.STATE_EXPANDED

        enableShadow(true)
    }


    private fun listenChange() {
        behavior?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        Log.d("$TAG  onStateChanged  ", "STATE_DRAGGING")
                    }

                    BottomSheetBehavior.STATE_SETTLING -> {
                        if (isExpanded) {
                            fab?.visibility = View.GONE
                            enableShadow(false)
                            behavior?.state = BottomSheetBehavior.STATE_HIDDEN
                        }
                        Log.d("$TAG  onStateChanged  ", "STATE_SETTLING")
                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {
                        isExpanded = true
                        Log.d("$TAG  onStateChanged  ", "STATE_EXPANDED")
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Log.d("$TAG  onStateChanged:  ", "STATE_COLLAPSED")
                        isExpanded = false
                        behavior.state = BottomSheetBehavior.STATE_HIDDEN
                        fab?.visibility = View.GONE
                        //                        enableShadow(false)
                        //                        view.fabSheet.visibility = View.GONE
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        Log.d("$TAG  onStateChanged  ", "STATE_HIDDEN")
                        isExpanded = false
                        fab?.visibility = View.GONE
                        removeShadow()
                        enableShadow(false)
                        //                        view.fabSheet.visibility = View.GONE
                        //                        view.fab.visibility=View.VISIBLE
                    }

                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        Log.d("$TAG  onStateChanged  ", "STATE_HALF_EXPANDED")

                    }


                }
            }
        })
    }

    private fun enableShadow(enable: Boolean) {
        if (backgroundView != null) {
            if (enable) {
                Utils.fadeIn(backgroundView!!)
                enableDisableViewGroup(view, false)
                backgroundView?.isEnabled = true
            } else {
                Utils.fadeOut(backgroundView!!)
                enableDisableViewGroup(view, true)
            }
        } else {
            enableDisableViewGroup(view, true)
        }

    }

    private fun hideBottomSheet() {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun isHide(): Boolean {
        return behavior.state == BottomSheetBehavior.STATE_HIDDEN
    }


    fun isCancelable(): Boolean {
        return customView?.style == SheetView.DIALOG_STYLE
    }

    override fun dismiss() {
        hideBottomSheet()
        customView = null
    }

    override fun isDismissed(): Boolean {
        return isHide()
    }

    override fun show() {
        showBottomSheet()
    }

    override fun hide() {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun isShow(): Boolean {
        return isHide()
    }

    override fun expand() {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun halfExpand() {
        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    override fun collapse() {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onClick(v: View?) {
        when (v) {
            fab -> {
                customView?.onFabClicked()
            }
        }
    }
}