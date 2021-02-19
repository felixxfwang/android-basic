package org.tiramisu.image

import android.graphics.Bitmap

interface ImageLoadCallback {

    fun onImageLoadSuccess(bitmap: Bitmap)

    fun onImageLoadFailed()
}

typealias ImageLoadFunction = (Boolean, Bitmap?) -> Unit