package com.pashkobohdan.multiwindowbrowser.ui.listeners

import android.animation.Animator

class AnimatorEndListener (val endCallback: ()->Unit) : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {
        //nop
    }

    override fun onAnimationEnd(animation: Animator?) {
        endCallback()
    }

    override fun onAnimationCancel(animation: Animator?) {
        //nop
    }

    override fun onAnimationStart(animation: Animator?) {
        //nop
    }
}