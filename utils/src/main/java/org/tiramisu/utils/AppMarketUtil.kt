package org.tiramisu.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

object AppMarketUtil {

    fun getGooglePlayUrl(packageName: String): String {
        return "https://play.google.com/store/apps/details?id=$packageName"
    }

    fun getMarketUrl(packageName: String): String {
        return "market://details?id=$packageName"
    }

    fun openGooglePlay(context: Context, packageName: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(getGooglePlayUrl(packageName))
            setPackage("com.android.vending")
        }
        context.startActivity(intent)
    }
}