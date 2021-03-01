package org.tiramisu.http

interface HttpParam {

    fun toMap(): Map<String, Any>
}