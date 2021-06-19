package org.tiramisu.utils.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Handler
import android.os.Looper

/**
 * @author felixxfwang
 */
class DelayedAnimationRepeater(private val delay: Long): AnimatorListenerAdapter() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onAnimationRepeat(animation: Animator?) {
        animation?.pause()
        handler.postDelayed({ animation?.resume() }, delay)
    }
}

class DelayedAnimatorRepeater(private val delay: Long): AnimatorListenerAdapter() {

    private var isCanceled = false

    override fun onAnimationStart(animation: Animator?) {
        isCanceled = false
    }

    override fun onAnimationCancel(animation: Animator?) {
        isCanceled = true
    }

    override fun onAnimationEnd(animation: Animator?) {
        if (!isCanceled) {
            animation?.startDelay = delay
            animation?.start()
        }
    }
}