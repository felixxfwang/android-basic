package org.tiramisu.utils

import android.Manifest.permission
import android.app.Activity
import com.blankj.utilcode.util.PhoneUtils
import org.tiramisu.permissions.TPermissions
import java.util.concurrent.atomic.AtomicReference

object DeviceUtil {

    private var imsi = AtomicReference<String>()
    private var mcc = AtomicReference<String>()
    private var mnc = AtomicReference<String>()

    fun getImsi(context: Activity): String {
        return if (imsi.get().isNullOrBlank()) {
            doGetImsi(context)
        } else {
            imsi.get()
        }
    }

    fun getMcc(): String = mcc.get() ?: StringUtil.EMPTY

    fun getMnc(): String = mnc.get() ?: StringUtil.EMPTY

    fun getMcc(context: Activity): String {
        return getImsi(context).let { if (it.length >= 3) it.substring(0, 3) else StringUtil.EMPTY }
    }

    fun getMnc(context: Activity): String {
        return getImsi(context).let { if (it.length >= 5) it.substring(3, 5) else StringUtil.EMPTY }
    }

    @Synchronized
    private fun doGetImsi(context: Activity): String {
        return if (TPermissions.hasPermissions(context, permission.READ_PHONE_STATE)) {
            onImsiQueried(PhoneUtils.getIMSI())
        } else {
            TPermissions.requestPermission(context, permission.READ_PHONE_STATE) { granted ->
                if (granted) onImsiQueried(PhoneUtils.getIMSI())
            }
            StringUtil.EMPTY
        }
    }

    private fun onImsiQueried(imsi: String): String {
        this.imsi.set(imsi)
        return imsi
    }

}