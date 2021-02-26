package org.tiramisu.animation.lottie

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.utils.MiscUtils
import org.tiramisu.animation.IAnimationController
import org.tiramisu.animation.IAnimationListener

/**
 * @author felixxfwang
 */
class LottieAnimationController(private val lottieDrawable: LottieDrawable): IAnimationController {
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun showFirstFrame() {
        lottieDrawable.cancelAnimation()
        lottieDrawable.progress = 0F
    }

    override fun showLastFrame() {
        lottieDrawable.cancelAnimation()
        lottieDrawable.progress = 1F
    }

    override fun start(from: Float, listener: IAnimationListener?) {
        checkAndDoAnimate(from, 1F, false, listener)
    }

    override fun animateTo(from: Float, to: Float, listener: IAnimationListener?) {
        checkAndDoAnimate(from, to, false, listener)
    }

    override fun repeat(from: Float, to: Float, listener: IAnimationListener?) {
        checkAndDoAnimate(from, to, true, listener)
    }

    private fun checkAndDoAnimate(from: Float, to: Float, isRepeat: Boolean, listener: IAnimationListener?) {
        if (lottieDrawable.composition == null) {
            handler.postDelayed({ checkAndDoAnimate(from, to, isRepeat, listener) }, 20)
        } else {
            doAnimate(isRepeat, from, to, listener)
        }
    }

    private fun doAnimate(isRepeat: Boolean, from: Float, to: Float, listener: IAnimationListener?) {
        // cancel
        lottieDrawable.cancelAnimation()
        lottieDrawable.removeAllAnimatorListeners()

        // setup
        lottieDrawable.repeatCount = if (isRepeat) LottieDrawable.INFINITE else 0
        setMinAndMaxProgress(from, to)
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

    @SuppressLint("RestrictedApi")
    private fun setMinAndMaxProgress(min: Float, max: Float) {
        val composition = lottieDrawable.composition
        lottieDrawable.setMinAndMaxFrame(
                MiscUtils.lerp(composition.startFrame, composition.endFrame, min).toInt(),
                MiscUtils.lerp(composition.startFrame, composition.endFrame, max).toInt()
        )
    }
}