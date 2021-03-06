package org.tiramisu.utils

import android.content.Context
import android.content.Intent

object ShareUtil {

    fun shareUrl(context: Context, url: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "image/jpeg"
        }
        context.startActivity(Intent.createChooser(shareIntent, "Choose Send Target"))
    }
}