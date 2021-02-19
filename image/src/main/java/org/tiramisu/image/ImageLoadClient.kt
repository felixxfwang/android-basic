package org.tiramisu.image

import android.widget.ImageView

interface ImageLoadClient {
    fun load(context: Any, view: ImageView, imageUri: Any?, options: ImageOptions? = null)

    fun load(context: Any, imageUri: Any?, callback: ImageLoadCallback)

    fun load(context: Any, imageUri: Any?, callback: ImageLoadFunction)
}

fun options() = ImageOptions()

class ImageOptions {
    var isCircle = false
    var loadingPlaceHolder = -1
    var errorPlaceHolder = -1

    fun asCircle(): ImageOptions {
        this.isCircle = true
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
