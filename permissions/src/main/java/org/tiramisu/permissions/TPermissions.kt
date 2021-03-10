package org.tiramisu.permissions

import com.blankj.utilcode.util.PermissionHelper
import java.util.concurrent.atomic.AtomicBoolean

object TPermissions {

    private val isRequesting = AtomicBoolean(false)

    fun isPermissionGranted(permission: String): Boolean {
        return PermissionHelper.isGranted(permission)
    }

    fun requestPermissions(request: PermissionRequest) {
        if (isRequesting.compareAndSet(false, true)) {
            PermissionHelper.permission(request.permissions)
                    .callback(object : PermissionHelper.FullCallback {
                        override fun onGranted(granted: MutableList<String>) {
                            isRequesting.set(false)
                            granted.forEach { request.callback?.onPermissionGranted(it) }
                        }

                        override fun onDenied(deniedForever: MutableList<String>, denied: MutableList<String>) {
                            isRequesting.set(false)
                            deniedForever.forEach { request.callback?.onPermissionDeniedForever(it) }
                            denied.forEach { request.callback?.onPermissionDenied(it) }
                        }

                    })
                    .request()
        }
    }

    fun requestPermissions(permission: String, callback: (Boolean) -> Unit) {
        val request = PermissionRequest(permission, callback = object : IPermissionCallback {
            override fun onPermissionGranted(permission: String) {
                callback.invoke(true)
            }

            override fun onPermissionDenied(permission: String) {
                callback.invoke(false)
            }

            override fun onPermissionDeniedForever(permission: String) {
                callback.invoke(false)
            }

        })
        requestPermissions(request)
    }
}