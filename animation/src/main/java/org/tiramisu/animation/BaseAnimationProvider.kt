package org.tiramisu.animation

import android.graphics.drawable.Drawable

/**
 * @author felixxfwang
 */
abstract class BaseAnimationProvider<D: Drawable>: IAnimationProvider {
    protected var drawable: D? = null
}