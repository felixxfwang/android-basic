package org.tiramisu.image

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData

val imageClient: ImageLoadClient = GlideImageClient()

class ImageRequest internal constructor(
    private val context: Any,
    private val view: ImageView
){
    private var options: ImageOptions? = null

    fun options(options: ImageOptions): ImageRequest {
        this.options = options
        return this
    }

    fun load(imageUri: Any?) {
        imageClient.load(context, view, imageUri, options)
    }
}

fun ImageView.with(fragment: Fragment) = ImageRequest(fragment, this)

fun ImageView.with(activity: FragmentActivity) = ImageRequest(activity, this)

fun ImageView.with(activity: Activity) = ImageRequest(activity, this)

fun ImageView.with(context: Context) = ImageRequest(context, this)

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, imageUrl: LiveData<String>?) {
    view.with(view.context).load(imageUrl?.value)
}

@BindingAdapter("assetSrc", "asCircle")
fun setAssertSrc(view: ImageView, assetSrc: String, asCircle: Boolean = false) {
    val request = view.with(view.context)
    if (asCircle) {
        request.options(options().asCircle())
    }
    request.load("file:///android_asset/$assetSrc")
}