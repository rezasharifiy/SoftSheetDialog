package com.rsmi.softsheet.util

import android.animation.Animator
import android.view.View
import android.view.animation.Animation

/**
 * Created by R.Sharifi
 * on 28 Feb 2021
 */
object Utils {

    fun fadeOut(view : View) {

        view.alpha = 1.0f
        view.visibility = View.VISIBLE

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        view.animate()
            .alpha(0.0f)
            .setDuration(200)
            .setListener(object : Animation.AnimationListener , Animator.AnimatorListener {
                override fun onAnimationStart(animation : Animation?) {
                }

                override fun onAnimationEnd(animation : Animation?) {
                    view.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation : Animation?) {
                }

                override fun onAnimationStart(animation : Animator?) {
                }

                override fun onAnimationEnd(animation : Animator?) {
                    view.visibility = View.GONE
                }

                override fun onAnimationCancel(animation : Animator?) {
                }

                override fun onAnimationRepeat(animation : Animator?) {
                }

            })


    }

    fun fadeIn(view : View) {
        view.alpha = 0f
        view.visibility = View.VISIBLE

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        view.animate()
            .alpha(1f)
            .setDuration(200)
            .setListener(null)
    }
}