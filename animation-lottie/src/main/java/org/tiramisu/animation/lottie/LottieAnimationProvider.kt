package org.tiramisu.animation.lottie

import android.content.Context
import android.widget.ImageView
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.google.auto.service.AutoService
import org.tiramisu.animation.BaseAnimationProvider
import org.tiramisu.animation.IAnimationController
import org.tiramisu.animation.IAnimationProvider
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author felixxfwang
 */
@AutoService(IAnimationProvider::class)
class LottieAnimationProvider: BaseAnimationProvider<LottieDrawable>() {
    private val lottieDrawable by lazy { LottieDrawable() }
    private val controller: IAnimationController by lazy { LottieAnimationController(lottieDrawable) }
    private val ready = AtomicBoolean(false)

    init {
        this.drawable = lottieDrawable
    }

    override fun source(context: Context, asset: String): IAnimationProvider {
        LottieCompositionFactory.fromAsset(context, asset)?.addListener {
            this.drawable?.composition = it
            ready.set(true)
        }
        return this
    }

    override fun into(view: ImageView): IAnimationProvider {
        view.setImageDrawable(this.drawable)
        return this
    }

    override fun isReady(): Boolean = ready.get()

    override fun animateController(): IAnimationController = controller

}