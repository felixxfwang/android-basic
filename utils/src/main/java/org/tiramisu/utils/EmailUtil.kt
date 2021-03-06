package org.tiramisu.utils

import android.content.Context
import android.content.Intent
import android.net.Uri


object EmailUtil {

    fun sendEmail(context: Context, toEmail: String) {
        val uri = Uri.parse("mailto:$toEmail")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        context.startActivity(Intent.createChooser(intent, "Choose Email"))
    }
}