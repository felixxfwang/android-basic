package org.tiramisu.utils

import android.Manifest.permission
import com.blankj.utilcode.util.PhoneUtils
import org.tiramisu.permissions.TPermissions
import java.util.concurrent.atomic.AtomicReference

object DeviceUtil {

    private var mImsi = AtomicReference<String>()
    private var mMcc = AtomicReference<String>()
    private var mMnc = AtomicReference<String>()

    init {
        doGetImsi(false)
        getMcc()
        getMnc()
    }

    fun mcc() = mMcc.get() ?: "NOT_READY"
    fun mnc() = mMnc.get() ?: "NOT_READY"

    fun getImsi(): String {
        return if (mImsi.get().isNullOrBlank()) {
            doGetImsi(true)
        } else {
            mImsi.get()
        }
    }

    fun getMcc(): String {
        if (mMcc.get().isNullOrBlank()) {
            mMcc.set(getImsi().let { if (it.length >= 3) it.substring(0, 3) else StringUtil.EMPTY })
        }
        return mMcc.get()
    }

    fun getMnc(): String {
        if (mMnc.get().isNullOrBlank()) {
            mMnc.set(getImsi().let { if (it.length >= 5) it.substring(3, 5) else StringUtil.EMPTY })
        }
        return mMnc.get()
    }

    @Synchronized
    private fun doGetImsi(requestPermission: Boolean = true): String {
        return when {
            TPermissions.isPermissionGranted(permission.READ_PHONE_STATE) -> {
                onImsiQueried(PhoneUtils.getIMSI())
            }
            requestPermission -> {
                TPermissions.requestPermissions(permission.READ_PHONE_STATE) { granted ->
                    if (granted) onImsiQueried(PhoneUtils.getIMSI())
                }
                StringUtil.EMPTY
            }
            else -> {
                StringUtil.EMPTY
            }
        }
    }

    private fun onImsiQueried(imsi: String): String {
        this.mImsi.set(imsi)
        return imsi
    }

}