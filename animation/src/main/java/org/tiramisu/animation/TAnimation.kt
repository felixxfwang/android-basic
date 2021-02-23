package org.tiramisu.animation

import java.util.*

/**
 * @author felixxfwang
 */
class TAnimation(
    private val animation: IAnimationProvider = loadDefaultAnimationProvider()
    ): IAnimationProvider by animation {

    companion object {
        fun loadDefaultAnimationProvider(): IAnimationProvider {
            return ServiceLoader.load(IAnimationProvider::class.java).firstOrNull() ?: DefaultAnimationProvider()
        }

        fun instance(): TAnimation =  Holder.instance
    }

    object Holder { val instance = TAnimation() }
}