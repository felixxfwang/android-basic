package org.tiramisu.animation.lottie

import com.airbnb.lottie.LottieDrawable
import org.tiramisu.animation.IAnimationController

/**
 * @author felixxfwang
 */
class LottieAnimationController(private val lottieDrawable: LottieDrawable): IAnimationController {

    private val frameCount = lottieDrawable.maxFrame

    override fun start(from: Float) {
        doAnimate(from, 1F, false)
    }

    override fun animateTo(from: Float, to: Float) {
        doAnimate(from, to, false)
    }

    override fun repeat(from: Float, to: Float) {
        doAnimate(from, to, true)
    }

    private fun doAnimate(from: Float, to: Float, isRepeat: Boolean) {
        lottieDrawable.endAnimation()
        lottieDrawable.repeatCount = if (isRepeat) LottieDrawable.INFINITE else 0
        lottieDrawable.setMinFrame(from.times(frameCount).toInt())
        lottieDrawable.setMaxFrame(to.times(frameCount).toInt())
        lottieDrawable.playAnimation()
    }
}