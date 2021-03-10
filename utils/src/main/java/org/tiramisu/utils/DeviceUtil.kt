package org.tiramisu.utils

import android.Manifest.permission
import com.blankj.utilcode.util.PhoneUtils
import org.tiramisu.permissions.TPermissions
import java.util.concurrent.atomic.AtomicReference

object DeviceUtil {

    private var mImsi = AtomicReference<String>()
    private var mMcc = AtomicReference<String>()
    private var mMnc = AtomicReference<String>()

    fun mcc() = mMcc.get() ?: "NOT_READY"
    fun mnc() = mMnc.get() ?: "NOT_READY"

    fun getImsi(): String {
        return if (mImsi.get().isNullOrBlank()) {
            doGetImsi()
        } else {
            mImsi.get()
        }
    }

    fun getMcc(): String {
        return if (mMcc.get().isNullOrBlank()) {
            getImsi().let { if (it.length >= 3) it.substring(0, 3) else StringUtil.EMPTY }
        } else {
            mMcc.get()
        }
    }

    fun getMnc(): String {
        return if (mMnc.get().isNullOrBlank()) {
            getImsi().let { if (it.length >= 5) it.substring(3, 5) else StringUtil.EMPTY }
        } else {
            mMnc.get()
        }
    }

    @Synchronized
    private fun doGetImsi(): String {
        return if (TPermissions.isPermissionGranted(permission.READ_PHONE_STATE)) {
            onImsiQueried(PhoneUtils.getIMSI())
        } else {
            TPermissions.requestPermissions(permission.READ_PHONE_STATE) { granted ->
                if (granted) onImsiQueried(PhoneUtils.getIMSI())
            }
            StringUtil.EMPTY
        }
    }

    private fun onImsiQueried(imsi: String): String {
        this.mImsi.set(imsi)
        return imsi
    }

}