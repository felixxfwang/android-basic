package org.tiramisu.image

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.net.URL

class GlideImageClient : ImageLoadClient {
    override fun load(context: Any, view: ImageView, imageUri: Any?, options: ImageOptions?) {
        request(context)?.let {
            val builder = load(it, imageUri)
            options(builder, options).into(view)
        }
    }

    override fun load(context: Any, imageUri: Any?, callback: ImageLoadCallback) {
        request(context)?.let {
            load(it.asBitmap(), imageUri).into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback.onImageLoadSuccess(resource)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    callback.onImageLoadFailed()
                }
            })
        }
    }

    override fun load(context: Any, imageUri: Any?, callback: ImageLoadFunction) {
        request(context)?.let {
            load(it.asBitmap(), imageUri).into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback.invoke(true, resource)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    callback.invoke(false, null)
                }

            })
        }
    }

    private fun request(context: Any): RequestManager? {
        return when (context) {
            is View -> Glide.with(context)
            is Fragment -> Glide.with(context)
            is FragmentActivity -> Glide.with(context)
            is Activity -> Glide.with(context)
            is Context -> Glide.with(context)
            else -> null
        }
    }

    private fun load(request: RequestManager, uri: Any?): RequestBuilder<Drawable> {
        return when (uri) {
            is String -> request.load(uri)
            is Int -> request.load(uri)
            is File -> request.load(uri)
            is Uri -> request.load(uri)
            is URL -> request.load(uri)
            is Drawable -> request.load(uri)
            is Bitmap -> request.load(uri)
            else -> request.load(uri)
        }
    }

    private fun load(request: RequestBuilder<Bitmap>, uri: Any?): RequestBuilder<Bitmap> {
        return when (uri) {
            is String -> request.load(uri)
            is Int -> request.load(uri)
            is File -> request.load(uri)
            is Uri -> request.load(uri)
            is URL -> request.load(uri)
            is Drawable -> request.load(uri)
            is Bitmap -> request.load(uri)
            else -> request.load(uri)
        }
    }

    private fun options(builder: RequestBuilder<Drawable>, options: ImageOptions?): RequestBuilder<Drawable> {
        return options?.let { opt ->
            when {
                opt.isCircle -> builder.circleCrop()
                opt.errorPlaceHolder != -1 -> builder.error(opt.errorPlaceHolder)
                opt.loadingPlaceHolder != -1 -> builder.placeholder(opt.loadingPlaceHolder)
            }
            builder
        } ?: builder
    }
}