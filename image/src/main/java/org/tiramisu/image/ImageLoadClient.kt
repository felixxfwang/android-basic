package org.tiramisu.image

import android.graphics.Color
import android.widget.ImageView

interface ImageLoadClient {
    fun load(context: Any, view: ImageView, imageUri: Any?, options: ImageOptions? = null)

    fun load(context: Any, imageUri: Any?, callback: ImageLoadCallback)

    fun load(context: Any, imageUri: Any?, callback: ImageLoadFunction)
}

fun options() = ImageOptions()

class ImageOptions {
    var isCircle = false
    var borderWidth = 0F
    var borderColor = Color.BLACK
    var loadingPlaceHolder = -1
    var errorPlaceHolder = -1

    fun asCircle(): ImageOptions {
        this.isCircle = true
        return this
    }

    fun borderWidth(borderWidth: Float): ImageOptions {
        this.borderWidth = borderWidth
        return this
    }

    fun borderColor(borderColor: Int): ImageOptions {
        this.borderColor = borderColor
        return this
    }

    fun placeholder(placeHolder: Int): ImageOptions {
        this.loadingPlaceHolder = placeHolder
        return this
    }

    fun error(placeHolder: Int): ImageOptions {
        this.errorPlaceHolder = placeHolder
        return this
    }
}
