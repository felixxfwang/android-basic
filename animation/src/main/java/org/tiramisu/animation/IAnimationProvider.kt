package org.tiramisu.animation

import android.content.Context
import android.widget.ImageView

interface IAnimationProvider {

    fun source(context: Context, asset: String): IAnimationProvider

    fun into(view: ImageView): IAnimationProvider

    fun isReady(): Boolean

    fun animateController(): IAnimationController
}