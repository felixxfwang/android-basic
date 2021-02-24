package org.tiramisu.animation.lottie

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import com.airbnb.lottie.LottieDrawable
import org.tiramisu.animation.IAnimationController
import org.tiramisu.animation.IAnimationListener

/**
 * @author felixxfwang
 */
class LottieAnimationController(private val lottieDrawable: LottieDrawable): IAnimationController {

    private val frameCount = lottieDrawable.maxFrame

    override fun start(from: Float, listener: IAnimationListener?) {
        doAnimate(from, 1F, false, listener)
    }

    override fun animateTo(from: Float, to: Float, listener: IAnimationListener?) {
        doAnimate(from, to, false, listener)
    }

    override fun repeat(from: Float, to: Float, listener: IAnimationListener?) {
        doAnimate(from, to, true, listener)
    }

    private fun doAnimate(from: Float, to: Float, isRepeat: Boolean, listener: IAnimationListener?) {
        // cancel
        lottieDrawable.cancelAnimation()
        lottieDrawable.removeAllAnimatorListeners()

        // setup
        lottieDrawable.repeatCount = if (isRepeat) LottieDrawable.INFINITE else 0
        lottieDrawable.setMinFrame(from.times(frameCount).toInt())
        lottieDrawable.setMaxFrame(to.times(frameCount).toInt())
        lottieDrawable.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                listener?.onAnimationStart()
            }

            override fun onAnimationEnd(animation: Animator?) {
                listener?.onAnimationEnd()
            }

            override fun onAnimationRepeat(animation: Animator?) {
                listener?.onAnimationRepeat()
            }
        })

        // restart
        lottieDrawable.playAnimation()
    }
}