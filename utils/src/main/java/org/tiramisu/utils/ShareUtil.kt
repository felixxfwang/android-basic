package org.tiramisu.utils

import android.content.Context
import android.content.Intent

object ShareUtil {

    fun shareUrl(context: Context, url: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(shareIntent, "Send to..."))
    }
}