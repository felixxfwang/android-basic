package org.tiramisu.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

object AppMarketUtil {

    fun openGooglePlay(context: Context, packageName: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            setPackage("com.android.vending")
        }
        context.startActivity(intent)
    }
}