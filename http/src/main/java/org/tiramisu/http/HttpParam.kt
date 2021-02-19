package org.tiramisu.http

abstract class HttpParam {

    abstract fun toMap(): Map<String, Any>
}