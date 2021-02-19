package org.tiramisu.http

interface HttpCallback<P: HttpParam, T> {

    fun onSuccess(param: P, data: T)

    fun onError(param: P, error: HttpException)
}