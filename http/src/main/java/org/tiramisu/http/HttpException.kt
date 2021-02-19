package org.tiramisu.http

class HttpException(val code: Int, message: String?, cause: Throwable? = null) : Exception(message, cause)