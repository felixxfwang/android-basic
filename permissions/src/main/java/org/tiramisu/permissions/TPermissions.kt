package org.tiramisu.permissions

import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object TPermissions {

    fun hasPermissions(context: Context, permission: String) : Boolean {
        return EasyPermissions.hasPermissions(context, permission)
    }

    fun requestPermission(context: Context, permission: String, callback: (Boolean) -> Unit) {
        if (hasPermissions(context, permission)) {
            callback.invoke(true)
        } else {
//            EasyPermissions.requestPermissions()
        }
    }
}