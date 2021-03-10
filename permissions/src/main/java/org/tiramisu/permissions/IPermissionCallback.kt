package org.tiramisu.permissions

interface IPermissionCallback {
    fun onPermissionGranted(permission: String)
    fun onPermissionDenied(permission: String)
    fun onPermissionDeniedForever(permission: String)
}