package org.tiramisu.animation

import android.widget.ImageView
import java.util.*

/**
 * @author felixxfwang
 */
class TAnimation(
    private val animation: IAnimationProvider = loadDefaultAnimationProvider()
    ): IAnimationProvider by animation {

    companion object {
        fun loadDefaultAnimationProvider(): IAnimationProvider {
            return ServiceLoader.load(IAnimationProvider::class.java).firstOrNull() ?: EmptyAnimationProvider()
        }
    }
}

fun ImageView.animate(assetName: String): TAnimation {
    return TAnimation().also {  it.source(context, assetName).into(this) }
}