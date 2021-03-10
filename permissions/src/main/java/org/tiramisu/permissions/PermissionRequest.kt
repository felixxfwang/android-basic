package org.tiramisu.permissions

class PermissionRequest(
    vararg var permissions: String,
    var callback: IPermissionCallback? = null
)