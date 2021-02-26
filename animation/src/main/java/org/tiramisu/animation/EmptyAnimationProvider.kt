package org.tiramisu.animation

import android.content.Context
import android.widget.ImageView

class EmptyAnimationProvider : IAnimationProvider {
    override fun source(context: Context, asset: String): IAnimationProvider = this

    override fun into(view: ImageView): IAnimationProvider = this

    override fun isReady(): Boolean = false

    override fun animateController(): IAnimationController = EmptyAnimationController()
}